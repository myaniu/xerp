package com.google.code.insect.workflow.comm;

public class InValidResourceException extends InternalWorkflowException {
	public InValidResourceException(String s) {
		super("不合法的资源：：" + s);
	}
}
