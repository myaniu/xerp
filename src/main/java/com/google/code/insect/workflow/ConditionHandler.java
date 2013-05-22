package com.google.code.insect.workflow;

public interface ConditionHandler {
	public boolean check(Token token, String variableName, Object... args);
}
