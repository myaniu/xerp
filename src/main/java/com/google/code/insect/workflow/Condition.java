package com.google.code.insect.workflow;

import java.io.Serializable;

public interface Condition extends Serializable {

	public boolean check(Token workFlow);

	public void add(Condition condition);

	public void remove(Condition condition);

	public Condition getChild(int i);

}
