package com.google.code.insect.workflow.comm;

public class InValidStateException extends InternalWorkflowException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InValidStateException(String s) {
		super("不合法的状态：" + s);
	}
}
