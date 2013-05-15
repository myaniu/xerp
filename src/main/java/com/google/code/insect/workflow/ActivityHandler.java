package com.google.code.insect.workflow;

public interface ActivityHandler{

	public void invoke(Token token,Object... args);
}
