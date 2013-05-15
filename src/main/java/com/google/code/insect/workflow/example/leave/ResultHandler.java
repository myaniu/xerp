package com.google.code.insect.workflow.example.leave;

import com.google.code.insect.workflow.ActivityHandler;
import com.google.code.insect.workflow.Token;

public class ResultHandler implements ActivityHandler {

	public void invoke(Token token, Object... args) {
		LeaveInfo leaveInfo = (LeaveInfo) token.getAttribute("LeaveInfo");
		if (leaveInfo.isAccept())
			System.out.println(leaveInfo.getStaff_name() + "的请假申请通过!");
		else
			System.out.println(leaveInfo.getStaff_name() + "的请假申请没有获得通过!");
	}

}
