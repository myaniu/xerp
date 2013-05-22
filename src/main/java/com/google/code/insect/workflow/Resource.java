package com.google.code.insect.workflow;

import java.io.Serializable;

public interface Resource extends Serializable {

	public void start(Transition transition, Token token, Object... args)
			throws InterruptedException;

	public long getId();

}
