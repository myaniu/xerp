package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.TransitionType;

public class EndTransition extends Transition {

	public EndTransition() {
		super();
		this.type = TransitionType.END;
	}

	public EndTransition(long id) {
		super(id);
		this.type = TransitionType.END;
	}

	public EndTransition(long id, String name) {
		super(id, name);
		this.type = TransitionType.END;
	}
}
