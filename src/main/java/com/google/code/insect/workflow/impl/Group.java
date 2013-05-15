package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.AbstractResource;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.ConditionException;
import com.google.code.insect.workflow.comm.InValidResourceException;

public class Group extends AbstractResource {

	public Group() {
		super();
	}

	public Group(long id, String name) {
		super();
		if (id <= 0)
			throw new InValidResourceException("群组id仅能为大于0的long型整数");
		this.id = id;
		this.name = name;
	}

	@Override
	public void doAction(Transition transition, Token token, Object... arg)
			throws InterruptedException {
	}

}
