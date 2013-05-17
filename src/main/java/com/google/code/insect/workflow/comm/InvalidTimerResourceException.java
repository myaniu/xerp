package com.google.code.insect.workflow.comm;

public class InvalidTimerResourceException extends InternalWorkflowException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTimerResourceException(String s) {
		super(s);
	}

	public InvalidTimerResourceException(String s, Throwable t) {
		super(s, t);
	}
}
