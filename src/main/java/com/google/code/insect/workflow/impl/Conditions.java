package com.google.code.insect.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.code.insect.workflow.Condition;
import com.google.code.insect.workflow.Token;


public class Conditions implements Condition {
	private List<Condition> conditonList;

	private String type;

	public List<Condition> getConditonList() {
		return conditonList;
	}

	public void setConditonList(List<Condition> conditonList) {
		this.conditonList = conditonList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Conditions() {
		super();
		this.conditonList = new ArrayList<Condition>();
		this.type = "and";
	}

	public void add(Condition condition) {
		conditonList.add(condition);
	}

	public boolean check(Token token) {
		if (this.type.trim().equalsIgnoreCase("and")) {
			boolean result = true;
			for (Condition condition : this.conditonList) {
				result = result && (condition.check(token));
				if (!result)
					break;
			}
			return result;
		} else if (this.type.trim().equalsIgnoreCase("or")) {
			boolean result = false;
			for (Condition condition : this.conditonList) {
				result = result || (condition.check(token));
				if (result)
					break;
			}
			return result;
		} else
			return false;
	}

	public Condition getChild(int i) {
		return conditonList.get(i);
	}

	public void remove(Condition condition) {
		conditonList.remove(condition);
	}
}
