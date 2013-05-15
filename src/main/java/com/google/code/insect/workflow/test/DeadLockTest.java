package com.google.code.insect.workflow.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.WorkFlow;
import com.google.code.insect.workflow.WorkFlowManager;
import com.google.code.insect.workflow.basic.BasicWorkflowManager;
import com.google.code.insect.workflow.comm.TransitionType;
import com.google.code.insect.workflow.config.DefaultConfiguration;
import com.google.code.insect.workflow.test.CompositeProcessTest.FlowThread;


public class DeadLockTest {
	private WorkFlowManager wm;

	WorkFlow composite;

	private CyclicBarrier barrier;

	private static final int total = 5000;

	public DeadLockTest() {
		this.barrier = new CyclicBarrier(total + 1);
		wm = new BasicWorkflowManager();
		wm.setConfiguration(new DefaultConfiguration());

		WorkFlow sequence = wm.getWorkFlow("sequence");
		WorkFlow concurrency = wm.getWorkFlow("concurrency");
		WorkFlow choose = wm.getWorkFlow("choose");

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
		for (int i = 0; i < total; i++) {
			new FlowThread().start();
		}
		barrier.await();
		long start = System.currentTimeMillis();
		barrier.await();
		long end = System.currentTimeMillis();
		System.out.println("创建" + total + "个流程并发运行完毕\n花费时间:" + (end - start)
				/ 1000.0 + "秒");
	}

	class FlowThread extends Thread {

		@Override
		public void run() {
			try {
				barrier.await();
				// wm = new BasicWorkflowManager();
				Token token1 = wm.startWorkFlow("composite");
				token1.setAttribute("name", "dennis");
				token1.setAttribute("num", 21);
				wm.doAction(token1.getId());
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	public static void main(String args[]) throws Exception {
		new DeadLockTest().testConcurrencyCompositeProcesss();
	}

}
