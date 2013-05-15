package com.google.code.insect.workflow.example.leave;

import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.WorkFlowManager;
import com.google.code.insect.workflow.basic.BasicWorkflowManager;
import com.google.code.insect.workflow.config.DefaultConfiguration;
import com.google.code.insect.workflow.impl.Group;
import com.google.code.insect.workflow.impl.User;

import junit.framework.TestCase;

public class LeaveTest extends TestCase {
	private WorkFlowManager wm;

	private Group employee, dept_manager, project_manager;

	private User dennis, green, jane;

	private long tokenId;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		wm = new BasicWorkflowManager();
		wm.setConfiguration(new DefaultConfiguration());
		this.employee = new Group(1, "employee");
		this.dept_manager = new Group(2, "dept_manager");
		this.project_manager = new Group(3, "project_manager");

		this.dennis = new User(1, "dennis zane", employee);
		this.green = new User(2, "Green", dept_manager);
		this.jane = new User(3, "andy jane", project_manager);

	}

	public void testAccept() throws Exception {
		// 填写请假信息
		LeaveInfo leaveInfo = new LeaveInfo();
		leaveInfo.setContent("本人请假三天");
		leaveInfo.setSubject("请假申请");
		leaveInfo.setStaff_name(dennis.getName());
		leaveInfo.setStaff_num(dennis.getId());
		leaveInfo.setAccept(true);

		// 启动一个案例
		Token token = wm.startWorkFlow("leave");
		token.setAttribute("LeaveInfo", leaveInfo);
		// 提交
		this.wm.doAction(token.getId(), this.dennis, "给领导发送消息："
				+ leaveInfo.getStaff_name() + "申请请假，请批准!");
		// 案例id，传递给需要处理的节点
		this.tokenId = token.getId();

		// 部门领导审批
		// 首先，根据tokenId获取案例
		Token token1 = this.wm.getToken(this.tokenId);
		LeaveInfo leaveInfo1 = (LeaveInfo) token1.getAttribute("LeaveInfo");
		leaveInfo1.setAccept(leaveInfo.isAccept() && true);
		this.wm.doAction(this.tokenId, green, "部门领导审批");

		// 项目领导审批
		// 首先，根据tokenId获取案例
		Token token2 = this.wm.getToken(this.tokenId);
		LeaveInfo leaveInfo2 = (LeaveInfo) token2.getAttribute("LeaveInfo");
		leaveInfo2.setAccept(leaveInfo2.isAccept() && true);
		this.wm.doAction(this.tokenId, jane, "项目领导审批");
		// this.wm.doAction(this.tokenId);

		Token token3 = this.wm.getToken(this.tokenId);
		assertTrue(token3.isFinished());
		System.out.println("花费时间:" + token3.getFinishTime() + "毫秒");

	}

}
