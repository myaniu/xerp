package com.google.code.insect.workflow.comm;

public class FactoryException extends InternalWorkflowException {
	public FactoryException(String s) {
		super(s);
	}

	public FactoryException(String s, Throwable t) {
		super(s, t);
	}
}
