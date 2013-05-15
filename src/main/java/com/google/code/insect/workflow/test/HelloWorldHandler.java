package com.google.code.insect.workflow.test;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.code.insect.workflow.ActivityHandler;
import com.google.code.insect.workflow.Token;


public class HelloWorldHandler implements ActivityHandler {
	public static AtomicInteger count = new AtomicInteger();

	public void invoke(Token token, Object... args) {
		// System.out.println("案例:" + token.getId() + " hello:"
		// + token.getAttribute("name"));
		count.incrementAndGet();
	}
}
