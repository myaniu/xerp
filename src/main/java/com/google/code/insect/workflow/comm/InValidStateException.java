package com.google.code.insect.workflow.comm;

public class InValidStateException extends InternalWorkflowException {
	public InValidStateException(String s) {
		super("不合法的状态：" + s);
	}
}
