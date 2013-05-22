package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.Place;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.TransitionType;

public class AndSplitTransition extends Transition {
	public AndSplitTransition() {
		super();
	}

	public AndSplitTransition(long id) {
		super(id);
	}

	public AndSplitTransition(long id, String name) {
		super(id, name);
	}

	@Override
	public TransitionType getType() {
		return TransitionType.AND_SPLIT;
	}

	@Override
	public void fire(Token token, Object... args) {
		for (Place input : this.inputs) {
			input.remove(token);
		}
		for (Place output : this.outputs) {
			Token copy = new Token();
			copy.setWorkFlow(token.getWorkFlow());
			copy.setId(token.getId());
			copy.setName(token.getName());
			copy.setFinished(token.isFinished());
			copy.setWorkFlow(token.getWorkFlow());
			copy.setStartTime(token.getStartTime());
			copy.setEndTime(token.getEndTime());
			output.put(copy);
		}
		if (this.activityHandler != null)
			this.activityHandler.invoke(token, args);

	}

}
