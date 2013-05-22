package com.google.code.insect.workflow.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.google.code.insect.workflow.AbstractResource;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.WorkFlowAlgorithm;
import com.google.code.insect.workflow.WorkFlowManager;
import com.google.code.insect.workflow.comm.Common;
import com.google.code.insect.workflow.comm.ConditionException;

public abstract class TimerResource extends AbstractResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int pool_size;

	protected static ScheduledExecutorService scheduledExecutorService;

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return Common.TIMER_RESOURCE_ID;
	}

	public TimerResource() {
		this.pool_size = 5;
		scheduledExecutorService = Executors.newScheduledThreadPool(pool_size);
	}

	public static void shutdownPool() {
		if (scheduledExecutorService != null)
			scheduledExecutorService.shutdown();
	}

	public final void start(Transition transition, Token token, Object... args)
			throws InterruptedException {
		if (transition.getCondition() != null
				&& !transition.getCondition().check(token))
			throw new ConditionException(transition.getName()
					+ " transition没有满足触发条件");
		transition.removeTokenFromInputs(token);
		doAction(transition, token, args);
	}

	protected class ChangeRunner implements Runnable {
		private Transition transition;

		private Token token;

		private Object[] args;

		public ChangeRunner(Transition transition, Token token, Object... args) {
			this.transition = transition;
			this.token = token;
			this.args = args;
		}

		public void run() {
			if (transition.getCondition() != null
					&& !transition.getCondition().check(token))
				throw new ConditionException(transition.getName()
						+ " transition没有满足触发条件");
			transition.addTokenToOutputs(token);
			Object real_args[] = new Object[args.length - 2];
			for (int i = 0; i < real_args.length; i++)
				real_args[i] = args[i + 2];
			transition.invokeHandler(token, real_args);
			try {
				// 回调
				((WorkFlowAlgorithm) args[1]).enabledTraversing(token
						.getWorkFlow());
				((WorkFlowManager) args[0]).doAction(token.getId());

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
