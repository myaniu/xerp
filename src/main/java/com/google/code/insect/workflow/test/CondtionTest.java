package com.google.code.insect.workflow.test;

import junit.framework.TestCase;

import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.basic.MemoryWorkFlowDAO;
import com.google.code.insect.workflow.impl.Conditions;
import com.google.code.insect.workflow.impl.SimpleCondition;

public class CondtionTest extends TestCase {
	private Token token;

	private Conditions condtions;

	@Override
	protected void setUp() throws Exception {
		this.token = new Token();
		token.setWorkFlowDAO(new MemoryWorkFlowDAO());
		this.condtions = new Conditions();
	}

	public void testSimpleCondtion() {
		SimpleCondition condition1 = new SimpleCondition();
		condtions.add(condition1);
		condition1.setExpression("age>30");
		condition1.setVariableName("age");
		token.setAttribute("age", 20);
		assertFalse(condtions.check(token));

		token.setAttribute("age", 30);
		assertFalse(condtions.check(token));

		token.setAttribute("age", 31);
		assertTrue(condtions.check(token));

		token.setAttribute("age", 50);
		assertTrue(condtions.check(token));

	}

	public void testComplexConditon() {
		SimpleCondition condition1 = new SimpleCondition();
		condition1.setExpression("age>20");
		condition1.setVariableName("age");

		SimpleCondition condition2 = new SimpleCondition();
		condition2.setExpression("money>100.2");
		condition2.setVariableName("money");

		condtions.setType("or");
		condtions.add(condition1);
		condtions.add(condition2);

		token.setAttribute("age", 18);
		token.setAttribute("money", 100.3);

		assertFalse(condition1.check(token));
		assertTrue(condition2.check(token));
		assertTrue(condtions.check(token));
		condtions.setType("and");
		assertFalse(condtions.check(token));

		Conditions another = new Conditions();
		another.add(condtions);
		SimpleCondition condition3 = new SimpleCondition();
		condition3.setExpression("age>15");
		condition3.setVariableName("age");

		assertTrue(condition3.check(token));
		another.add(condition3);

		assertFalse(another.check(token));
		condtions.setType("or");
		assertTrue(another.check(token));
		another.setType("or");
		assertTrue(another.check(token));
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

}
