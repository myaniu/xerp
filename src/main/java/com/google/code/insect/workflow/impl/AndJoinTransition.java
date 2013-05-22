package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.TransitionType;

public class AndJoinTransition extends Transition {
	public AndJoinTransition() {
		super();
		this.type = TransitionType.AND_JOIN;

	}

	public AndJoinTransition(long id) {
		super(id);
		this.type = TransitionType.AND_JOIN;
	}

	public AndJoinTransition(long id, String name) {
		super(id, name);
		this.type = TransitionType.AND_JOIN;
	}
}
