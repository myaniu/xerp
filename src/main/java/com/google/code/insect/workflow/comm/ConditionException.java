package com.google.code.insect.workflow.comm;

public class ConditionException extends InternalWorkflowException {
	public ConditionException(String s) {
		super(s);
	}

	public ConditionException(String s, Throwable t) {
		super(s, t);
	}
}
