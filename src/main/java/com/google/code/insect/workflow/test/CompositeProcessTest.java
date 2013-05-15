package com.google.code.insect.workflow.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import com.google.code.insect.workflow.Place;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.WorkFlow;
import com.google.code.insect.workflow.WorkFlowManager;
import com.google.code.insect.workflow.basic.BasicWorkflowManager;
import com.google.code.insect.workflow.comm.TransitionType;
import com.google.code.insect.workflow.config.DefaultConfiguration;
import com.google.code.insect.workflow.impl.FixTimerResource;
import com.google.code.insect.workflow.impl.Group;
import com.google.code.insect.workflow.impl.User;
import com.google.code.insect.workflow.util.DateTimeUtil;

import junit.framework.TestCase;

public class CompositeProcessTest extends TestCase {
	private WorkFlowManager wm;

	WorkFlow composite;

	private CyclicBarrier barrier;

	private static final int total = 1;

	@Override
	protected void setUp() throws Exception {
		this.barrier = new CyclicBarrier(total + 1);
		wm = new BasicWorkflowManager();
		WorkFlow sequence = wm.getWorkFlow("sequence");
		WorkFlow concurrency = wm.getWorkFlow("concurrency");
		WorkFlow choose = wm.getWorkFlow("choose");

		long next = System.currentTimeMillis() + 10000;
		Date date = new Date(next);
		String at = DateTimeUtil.getDateTime(date, "yyyy-MM-dd HH:mm:ss");
		((FixTimerResource) concurrency.getTransition(3).getResource())
				.setAt(at);

		// 组合流程
		composite = new WorkFlow();
		composite.setName("composite");
		composite.setId(100);

		wm.saveWorkFlow(composite);

		// 修改开始结束节点的输入输出库所
		sequence.getEnd().setType(TransitionType.NORMAL);
		sequence.getEnd().setOutputs(concurrency.getStart().getInputs());

		concurrency.getEnd().setType(TransitionType.NORMAL);
		concurrency.getEnd().setOutputs(choose.getStart().getInputs());

		composite.setStart(sequence.getStart());
		composite.setEnd(choose.getEnd());
		List<Transition> transitions = new ArrayList<Transition>();
		transitions.addAll(sequence.getTransitions());
		transitions.addAll(concurrency.getTransitions());
		transitions.addAll(choose.getTransitions());
		composite.setTransitions(transitions);
	}

	public void testConcurrencyCompositeProcesss() throws Exception {
		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < total; i++) {
			FlowThread temp = new FlowThread();
			temp.start();
			list.add(temp);
		}
		barrier.await();
		barrier.await();
		long end = System.currentTimeMillis();
		Thread.sleep(30000);

		System.out.println("hello count:" + HelloWorldHandler.count.get());
		System.out.println("calc count:" + CalculateHandler.count.get());
		for (Transition transition : composite.getTransitions()) {
			assertFalse(transition.isEnable());
			System.out.println("transition" + transition.getId());
			for (Place place : transition.getOutputs()) {
				System.out.println("place" + place.getId() + "  "
						+ place.getTokens().size());
				assertEquals(0, place.getTokens().size());
			}
		}
	}

	class FlowThread extends Thread {

		@Override
		public void run() {
			try {
				barrier.await();
				// wm = new BasicWorkflowManager();
				WorkFlow concurrency = wm.getWorkFlow("concurrency");
				long next = System.currentTimeMillis() + 10000;
				Date date = new Date(next);
				String at = DateTimeUtil.getDateTime(date,
						"yyyy-MM-dd HH:mm:ss");
				((FixTimerResource) concurrency.getTransition(3).getResource())
						.setAt(at);
				Token token = wm.startWorkFlow("composite");
				User dennis = new User(2, "dennis");
				Group admin = new Group();
				admin.setId(1);
				dennis.setGroup(admin);
				token.setAttribute("name", "dennis");
				token.setAttribute("num", 30);
				wm.doAction(token.getId(), dennis, null);
				sleep(5000);
				wm.doAction(token.getId(), dennis, null);
				sleep(17000);
				assertTrue(token.isFinished());
				System.out.println("案例" + token.getId() + "花费:"
						+ token.getFinishTime() + "毫秒");
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
