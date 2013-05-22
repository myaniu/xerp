package com.google.code.insect.workflow.comm;

public class InValidResourceException extends InternalWorkflowException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InValidResourceException(String s) {
		super("不合法的资源：：" + s);
	}
}
