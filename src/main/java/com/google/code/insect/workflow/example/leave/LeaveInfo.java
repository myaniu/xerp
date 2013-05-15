package com.google.code.insect.workflow.example.leave;

import java.io.Serializable;

public class LeaveInfo implements Serializable {
	private int id;

	private String staff_name;

	private long staff_num;

	private String subject;

	private String content;

	private transient boolean accept;

	public LeaveInfo() {

	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public LeaveInfo(int id, String staff_name, long staff_num, String subject,
			String content) {
		super();
		this.id = id;
		this.staff_name = staff_name;
		this.staff_num = staff_num;
		this.subject = subject;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public long getStaff_num() {
		return staff_num;
	}

	public void setStaff_num(long staff_num) {
		this.staff_num = staff_num;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
