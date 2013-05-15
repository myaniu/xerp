package com.google.code.insect.workflow.test;

import com.google.code.insect.workflow.Place;
import com.google.code.insect.workflow.Resource;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.WorkFlow;
import com.google.code.insect.workflow.WorkFlowDAO;
import com.google.code.insect.workflow.WorkFlowManager;
import com.google.code.insect.workflow.basic.BasicWorkflowManager;
import com.google.code.insect.workflow.basic.MemoryWorkFlowDAO;
import com.google.code.insect.workflow.impl.AndSplitTransition;
import com.google.code.insect.workflow.impl.EndTransition;
import com.google.code.insect.workflow.impl.StartTransition;
import com.google.code.insect.workflow.impl.User;

import junit.framework.TestCase;

public class BasicWorkFlowTest extends TestCase {
	private WorkFlowDAO workFlowDAO;

	private Resource dennis, yuehua, jordan;

	WorkFlowManager wm;

	@Override
	protected void setUp() throws Exception {
		this.workFlowDAO = new MemoryWorkFlowDAO();
		this.dennis = new User(1, "dennis zane");
		this.yuehua = new User(2, "chen yuehua");
		this.jordan = new User(2, "jordan");
		wm = new BasicWorkflowManager(this.workFlowDAO);
	}

	public void testAndRoute() throws Exception {
		Place a = new Place(0);
		Place b = new Place(1);
		Place c = new Place(3);
		Place d = new Place(4);
		Place e = new Place(5);
		Place f = new Place(6);
		Place g = new Place(7);
		Place h = new Place(8);
		Place i = new Place(9);
		Transition start = new StartTransition(1, "start");
		start.addInputPlace(a);
		start.addOutputPlace(b);

		Transition andSplit = new AndSplitTransition(2, "And Split");
		andSplit.addInputPlace(b);
		andSplit.addOutputPlace(c);
		andSplit.addOutputPlace(d);

		Transition hello = new Transition(3, "hello");
		hello.addInputPlace(c);
		hello.addOutputPlace(e);
		hello.setResource(dennis);
		hello.setActivityHandler(new HelloWorldHandler());

		Transition calc = new Transition(4, "calc");
		calc.addInputPlace(d);
		calc.addOutputPlace(f);
		calc.setResource(yuehua);
		calc.setActivityHandler(new CalculateHandler());

		Transition andJoin = new Transition(5, "And Join");
		andJoin.addInputPlace(e);
		andJoin.addInputPlace(f);
		andJoin.addOutputPlace(g);

		Transition goodbye = new Transition(6, "say goodbye");
		goodbye.addInputPlace(g);
		goodbye.addOutputPlace(h);
		goodbye.setActivityHandler(new HelloWorldHandler());

		Transition end = new EndTransition(7, "end");
		end.addInputPlace(h);
		end.addOutputPlace(i);

		WorkFlow wf = new WorkFlow();
		wf.setName("test2");
		wf.setId(2);
		wf.addTransition(start);
		wf.addTransition(andSplit);
		wf.addTransition(hello);
		wf.addTransition(calc);
		wf.addTransition(andJoin);
		wf.addTransition(goodbye);
		wf.addTransition(end);
		wf.setStart(start);
		wf.setEnd(end);
		workFlowDAO.saveWorkFlow(wf);

		Token token1 = wm.startWorkFlow(2);
		token1.setAttribute("name", "just for test");
		token1.setAttribute("num", 100);
		wm.doAction(token1.getId());
		assertFalse(start.isEnable());
		assertFalse(andSplit.isEnable());
		assertTrue(hello.isEnable());
		assertTrue(calc.isEnable());

		wm.doAction(token1.getId(), dennis);
		assertFalse(hello.isEnable());
		assertFalse(andJoin.isEnable());
		assertFalse(end.isEnable());
		assertTrue(calc.isEnable());
		assertFalse(token1.isFinished());

		wm.doAction(token1.getId(), yuehua);
		assertFalse(hello.isEnable());
		assertFalse(calc.isEnable());
		assertTrue(token1.isFinished());
		assertFalse(end.isEnable());

	}

	public void testSequence() throws Exception {

		Place a = new Place(0);
		Place b = new Place(1);
		Place c = new Place(3);
		Place d = new Place(4);
		Place e = new Place(5);
		Place f = new Place(6);

		Transition start = new StartTransition(1, "start");
		start.addInputPlace(a);
		start.addOutputPlace(b);

		Transition second = new Transition(2, "secnond");
		second.addInputPlace(b);
		second.addOutputPlace(c);
		second.setResource(dennis);
		second.setActivityHandler(new HelloWorldHandler());

		Transition three = new Transition(3, "three");
		three.addInputPlace(c);
		three.addOutputPlace(d);
		three.setResource(yuehua);
		three.setActivityHandler(new HelloWorldHandler());

		Transition four = new Transition(4, "four");
		four.addInputPlace(d);
		four.addOutputPlace(e);
		four.setResource(dennis);
		four.setActivityHandler(new CalculateHandler());

		Transition end = new EndTransition(5, "end");
		end.addInputPlace(e);
		end.addOutputPlace(f);

		WorkFlow wf = new WorkFlow();
		wf.setName("test1");
		wf.setId(0);
		wf.addTransition(start);
		wf.addTransition(second);
		wf.addTransition(three);
		wf.addTransition(four);
		wf.addTransition(end);
		wf.setStart(start);
		wf.setEnd(end);
		workFlowDAO.saveWorkFlow(wf);

		Token token1 = wm.startWorkFlow(0);
		Token token2 = wm.startWorkFlow(0);
		Token token3 = wm.startWorkFlow(0);
		token1.setAttribute("name", "dennis");
		token2.setAttribute("name", "jordan");
		token3.setAttribute("name", "test3");
		assertFalse(token1.isFinished());
		assertFalse(token2.isFinished());
		assertFalse(token3.isFinished());
		// Token token=wm.startWorkFlow("test");
		// wm.doAction(token.getId());
		wm.doAction(token1.getId(), dennis);
		wm.doAction(token2.getId(), dennis);
		wm.doAction(token1.getId(), yuehua);
		wm.doAction(token2.getId(), yuehua);
		wm.doAction(token3.getId(), dennis);
		token1.setAttribute("num", 100);
		token2.setAttribute("num", 1000);
		token3.setAttribute("num", 50);
		assertFalse(token1.isFinished());
		assertFalse(token2.isFinished());
		assertFalse(token3.isFinished());
		wm.doAction(token1.getId(), dennis);
		wm.doAction(token2.getId(), dennis);
		wm.doAction(token3.getId(), yuehua);
		wm.doAction(token3.getId(), dennis);
		assertTrue(token1.isFinished());
		assertTrue(token2.isFinished());
		assertTrue(token3.isFinished());
		// Thread.sleep(10000);

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

}
