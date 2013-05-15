package com.google.code.insect.workflow.test;

import com.google.code.insect.workflow.ActivityHandler;
import com.google.code.insect.workflow.Token;

public class IncrementNumHandler implements ActivityHandler {

	public void invoke(Token token, Object... args) {
		int n = (Integer) token.getAttribute("num");
		n++;
		token.setAttribute("num", n);
		// System.out.println(n);
	}
}
