package com.google.code.insect.workflow.impl;

import java.math.BigDecimal;

import com.google.code.insect.workflow.Condition;
import com.google.code.insect.workflow.ConditionHandler;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.comm.ConditionException;
import com.google.code.insect.workflow.util.SimpleExpressionParser;


public class SimpleCondition implements Condition {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7217477134915724256L;

	private String expression;

	private ConditionHandler handler;

	private Object value;

	private String variableName;

	public void add(Condition condition) {
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public ConditionHandler getHandler() {
		return handler;
	}

	public void setHandler(ConditionHandler handler) {
		this.handler = handler;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public boolean check(Token token) {
		if (handler != null)
			return handler.check(token, this.variableName, this.value);
		else if (expression == null)
			throw new ConditionException("条件没有设置表达式");
		else {
			if (value == null && variableName != null) {
				BigDecimal fact = new BigDecimal(String.valueOf(token
						.getAttribute(variableName)));
				if (fact == null)
					throw new IllegalArgumentException("context中没有变量"
							+ variableName);
				return SimpleExpressionParser.getInstance().fireRule(
						expression, fact);
			} else if (value != null) {
				return SimpleExpressionParser.getInstance().fireRule(
						expression, (BigDecimal) value);
			} else if (value == null && variableName == null)
				return SimpleExpressionParser.getInstance().fireRule(
						expression, null);
			return false;
		}

	}

	public Condition getChild(int i) {
		return null;
	}

	public void remove(Condition condition) {
	}

}
