package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.AbstractResource;
import com.google.code.insect.workflow.Resource;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.InValidResourceException;

public class User extends AbstractResource {
	protected Group group;

	public User() {
		super();
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User(long id, String name) {
		super();
		if (id <= 0)
			throw new InValidResourceException("用户id仅能为大于0的long型整数");
		this.id = id;
		this.name = name;
	}

	public User(long id, String name, Group group) {
		super();
		if (id <= 0)
			throw new InValidResourceException("用户id仅能为大于0的long型整数");
		this.id = id;
		this.name = name;
		this.group = group;
	}

	@Override
	public void doAction(Transition transition, Token token, Object... arg)
			throws InterruptedException {
	}

}
