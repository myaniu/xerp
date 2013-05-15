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
import com.google.code.insect.workflow.impl.AutoResource;
import com.google.code.insect.workflow.impl.Group;
import com.google.code.insect.workflow.impl.User;

import junit.framework.TestCase;

public class PerformanceTest extends TestCase {
	private WorkFlowManager wm;

	WorkFlow composite;

	private CyclicBarrier barrier;

	private static final int total = 1000;

	@Override
	protected void setUp() throws Exception {
		this.barrier = new CyclicBarrier(total + 1);
		wm = new BasicWorkflowManager();
		WorkFlow sequence = wm.getWorkFlow("sequence");
		WorkFlow concurrency = wm.getWorkFlow("concurrency");
		WorkFlow choose = wm.getWorkFlow("choose");
		WorkFlow loop = wm.getWorkFlow("loop");

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

		choose.getEnd().setType(TransitionType.NORMAL);
		choose.getEnd().setOutputs(loop.getStart().getInputs());

		composite.setStart(sequence.getStart());
		composite.setEnd(loop.getEnd());
		List<Transition> transitions = new ArrayList<Transition>();
		transitions.addAll(sequence.getTransitions());
		transitions.addAll(concurrency.getTransitions());
		transitions.addAll(choose.getTransitions());
		transitions.addAll(loop.getTransitions());
		for (Transition transition : transitions) {
			transition.setResource(new AutoResource()); // 全部设为自动执行
		}
		composite.setTransitions(transitions);
	}

	public void testSingleThread() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			Token token = wm.startWorkFlow("composite");
			User dennis = new User(2, "dennis");
			Group admin = new Group();
			admin.setId(1);
			dennis.setGroup(admin);
			token.setAttribute("name", "dennis");
			token.setAttribute("num", 21);
			wm.doAction(token.getId());
			for (int j = 22; j < 30; j++)
				wm.doAction(token.getId());
			assertTrue(token.isFinished());
		}
		long end = System.currentTimeMillis();
		System.out.println("单线程运行" + total + "个案例花费时间：" + (end - start)
				/ 1000.0f + "秒");

	}

	public void testConcurrencyPeformance() throws Exception {
		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < total; i++) {
			FlowThread temp = new FlowThread();
			temp.start();
			list.add(temp);

		}

		barrier.await();
		long start = System.currentTimeMillis();
		barrier.await();
		long end = System.currentTimeMillis();
		System.out.println("并发" + total + "个线程花费时间：" + (end - start) / 1000.0f
				+ "秒");
		// System.out.println("hello count:" + HelloWorldHandler.count.get());
		// System.out.println("calc count:" + CalculateHandler.count.get());
		// for (Transition transition : composite.getTransitions()) {
		// assertFalse(transition.isEnable());
		// System.out.println("transition" + transition.getId());
		// for (Place place : transition.getOutputs()) {
		// System.out.println("place" + place.getId() + " "
		// + place.getTokens().size());
		// assertEquals(0, place.getTokens().size());
		// }
		// }
	}

	class FlowThread extends Thread {

		@Override
		public void run() {
			try {
				barrier.await();
				Token token = wm.startWorkFlow("composite");
				User dennis = new User(2, "dennis");
				Group admin = new Group();
				admin.setId(1);
				dennis.setGroup(admin);
				token.setAttribute("name", "dennis");
				token.setAttribute("num", 21);
				wm.doAction(token.getId());
				for (int j = 22; j < 30; j++)
					wm.doAction(token.getId());
				assertTrue(token.isFinished());
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
