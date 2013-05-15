package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.TransitionType;

public class StartTransition extends Transition {

	public StartTransition() {
		super();
		this.type = TransitionType.START;
	}

	public StartTransition(long id) {
		super(id);
		this.type = TransitionType.START;
	}

	public StartTransition(long id, String name) {
		super(id, name);
		this.type = TransitionType.START;
	}

}
