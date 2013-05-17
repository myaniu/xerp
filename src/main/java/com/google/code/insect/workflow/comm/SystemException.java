package com.google.code.insect.workflow.comm;

public class SystemException extends InternalWorkflowException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemException(String s) {
		super(s);
	}

	public SystemException(String s, Throwable t) {
		super(s, t);
	}
}
