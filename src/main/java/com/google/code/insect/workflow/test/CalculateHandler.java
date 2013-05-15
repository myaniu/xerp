package com.google.code.insect.workflow.test;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.code.insect.workflow.ActivityHandler;
import com.google.code.insect.workflow.Token;


public class CalculateHandler implements ActivityHandler {

	public static AtomicInteger count = new AtomicInteger();

	public void invoke(Token token, Object... args) {
		int n = (Integer) token.getAttribute("num");

		int result = 0;
		for (int i = 0; i < n; i++)
			result += i;
		count.incrementAndGet();
		// System.out.println("案例:" + token.getId() + " sum(" + n + ")=" +
		// result);
	}

}
