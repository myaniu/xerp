package com.google.code.insect.workflow.example.leave;

import com.google.code.insect.workflow.ActivityHandler;
import com.google.code.insect.workflow.Token;

public class SendRemindHandler implements ActivityHandler {

	public void invoke(Token token, Object... args) {
		System.out.println(args[0]);
	}
}
