package com.google.code.insect.workflow.test;

import java.util.Date;

import com.google.code.insect.workflow.Place;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.WorkFlow;
import com.google.code.insect.workflow.WorkFlowManager;
import com.google.code.insect.workflow.basic.BasicWorkflowManager;
import com.google.code.insect.workflow.comm.AuthorizationFailureException;
import com.google.code.insect.workflow.comm.InValidStateException;
import com.google.code.insect.workflow.comm.SystemException;
import com.google.code.insect.workflow.config.DefaultConfiguration;
import com.google.code.insect.workflow.impl.FixTimerResource;
import com.google.code.insect.workflow.impl.Group;
import com.google.code.insect.workflow.impl.User;
import com.google.code.insect.workflow.util.DateTimeUtil;

import junit.framework.TestCase;

public class BasicWorkFlowManagerXmlTest extends TestCase {
	private WorkFlowManager wm;

	private static final int total = 10;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		wm = new BasicWorkflowManager();
	}

	public void testSequence() throws Exception {
		Token token1 = wm.startWorkFlow("sequence");
		token1.setAttribute("name", "dennis");
		token1.setAttribute("num", 500);
		wm.doAction(token1.getId(), new User(1, "dennis"));
		assertFalse(token1.isFinished());
		wm.doAction(token1.getId(), new User(2, "dennis"));
		assertFalse(token1.isFinished());
		Thread.sleep(3000);
		for (Transition transition : token1.getWorkFlow().getTransitions()) {
			for (Place place : transition.getOutputs()) {
				assertEquals(0, place.getTokens().size());
				// System.out.println("place " + place.getId() + " "
				// + place.getTokens().size());
			}
		}
		assertTrue(token1.isFinished());

	}

	public void testConcurrency() throws Exception {

		WorkFlow concurrency = wm.getWorkFlow("concurrency");
		long next = System.currentTimeMillis() + 5000;
		Date date = new Date(next);
		String at = DateTimeUtil.getDateTime(date, "yyyy-MM-dd HH:mm:ss");
		((FixTimerResource) concurrency.getTransition(3).getResource())
				.setAt(at);

		Token token1 = wm.startWorkFlow("concurrency");
		token1.setAttribute("name", "dennis");
		token1.setAttribute("num", 100);
		wm.doAction(token1.getId());
		assertFalse(token1.isFinished());
		User dennis = new User(2, "dennis");
		Group admin = new Group();
		admin.setId(1);
		dennis.setGroup(admin);
		wm.doAction(token1.getId(), dennis, null);
		assertFalse(token1.isFinished());

		Thread.sleep(8000);

		for (Transition transition : token1.getWorkFlow().getTransitions()) {
			for (Place place : transition.getOutputs()) {
				// System.out.println("place " + place.getId() + " "
				// + place.getTokens().size());
				assertEquals(0, place.getTokens().size());
			}
		}
		assertTrue(token1.isFinished());
	}

	public void testChoose() throws Exception {
		Token token1 = wm.startWorkFlow("choose");
		token1.setAttribute("num", 30);
		try {
			wm.doAction(token1.getId());
			fail();
		} catch (InValidStateException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			System.out.println("shit");
			assertTrue(true);
		}
		token1.setAttribute("name", "dennis");
		try {
			wm.doAction(token1.getId());
		} catch (InValidStateException e) {
			System.out.println("shit2");
			e.printStackTrace();
		} catch (SystemException e) {
			System.out.println("shit3");
			fail();
		}
		for (Transition transition : token1.getWorkFlow().getTransitions()) {
			for (Place place : transition.getOutputs()) {
				assertEquals(0, place.getTokens().size());
			}
		}
		assertTrue(token1.isFinished());
	}

	public void testLoop() throws Exception {
		Token token1 = wm.startWorkFlow("loop");
		token1.setAttribute("num", 0);
		for (int j = 0; j < 30; j++)
			wm.doAction(token1.getId());
		for (Transition transition : token1.getWorkFlow().getTransitions()) {
			for (Place place : transition.getOutputs()) {
				// System.out.println("place " + place.getId() + " "
				// + place.getTokens().size());
				assertEquals(0, place.getTokens().size());
			}
		}
		assertTrue(token1.isFinished());
		assertEquals(30, ((Integer) (token1.getAttribute("num"))).intValue());
	}

	@Override
	protected void tearDown() throws Exception {
		// wm.stop();
		wm = null;
	}

}
