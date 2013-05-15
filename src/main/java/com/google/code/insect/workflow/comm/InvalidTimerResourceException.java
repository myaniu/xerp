package com.google.code.insect.workflow.comm;

public class InvalidTimerResourceException extends InternalWorkflowException {
	public InvalidTimerResourceException(String s) {
		super(s);
	}

	public InvalidTimerResourceException(String s, Throwable t) {
		super(s, t);
	}
}
