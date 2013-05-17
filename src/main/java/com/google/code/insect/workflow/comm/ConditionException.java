package com.google.code.insect.workflow.comm;

public class ConditionException extends InternalWorkflowException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConditionException(String s) {
		super(s);
	}

	public ConditionException(String s, Throwable t) {
		super(s, t);
	}
}
