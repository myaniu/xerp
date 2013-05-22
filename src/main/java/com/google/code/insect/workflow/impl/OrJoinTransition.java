package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.TransitionType;

public class OrJoinTransition extends Transition {
	public OrJoinTransition() {
		super();
		this.type = TransitionType.OR_JOIN;

	}

	public OrJoinTransition(long id) {
		super(id);
		this.type = TransitionType.OR_JOIN;
	}

	public OrJoinTransition(long id, String name) {
		super(id, name);
		this.type = TransitionType.OR_JOIN;
	}
}
