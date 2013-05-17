package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.AbstractResource;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.Common;

public final class AutoResource extends AbstractResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutoResource() {
	}

	public long getId() {
		return Common.AUTO_RESOURCE_ID;
	}

	@Override
	public void doAction(Transition transition, Token token, Object... arg)
			throws InterruptedException {
	}
}
