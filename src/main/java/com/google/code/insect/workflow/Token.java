package com.google.code.insect.workflow;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * <p>
 * 类说明：
 * </p>
 * <p>
 * petri net中的token，也就是所谓案例，工作流的具体实例
 * </p>
 * 
 * @author dennis
 * 
 */
@Table("wf_Token")
public  class Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	@Column
	private String name;
	@Column
	private boolean finished;

	private WorkFlowDAO workFlowDAO;

	private WorkFlow workFlow;
	@Column
	private long startTime;
	@Column
	private long endTime;

	public Token() {
		this.startTime = System.currentTimeMillis();
	}

	public Token(WorkFlow wf, WorkFlowDAO workFlowDAO) {
		this.startTime = System.currentTimeMillis();
		this.workFlow = wf;
		this.workFlowDAO = workFlowDAO;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
		if (this.finished) {
			this.endTime = System.currentTimeMillis();
		}
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public WorkFlowDAO getWorkFlowDAO() {
		return workFlowDAO;
	}

	public void setWorkFlowDAO(WorkFlowDAO workFlowDAO) {
		this.workFlowDAO = workFlowDAO;
	}

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object setAttribute(Object key, Object value) {
		return this.workFlowDAO.setAttribute(this, key, value);
	}

	public Object getAttribute(Object key) {
		return this.workFlowDAO.getAttribute(this, key);
	}

	public Object removeAttribute(Object key) {
		return this.workFlowDAO.removeAttribute(this, key);
	}

	@Override
	public boolean equals(Object obj) {
		Token other = (Token) obj;
		return this.id == other.getId();
	}

	public long getFinishTime() {
		if (this.finished)
			return (this.endTime - this.startTime);
		else
			return -1;
	}

}
