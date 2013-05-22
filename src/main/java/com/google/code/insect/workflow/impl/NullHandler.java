package com.google.code.insect.workflow.impl;

import com.google.code.insect.workflow.ConditionHandler;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.comm.ConditionException;

public class NullHandler implements ConditionHandler {

	public boolean check(Token token, String viriableName, Object... args) {
		boolean isNull = Boolean.parseBoolean((String) args[0]);
		if (viriableName == null)
			throw new ConditionException("NullHandler没有设置判断的变量名");
		if (isNull)
			return token.getAttribute(viriableName) == null;
		else
			return token.getAttribute(viriableName) != null;
	}

}
