package com.google.code.insect.workflow;

import com.google.code.insect.workflow.config.Configuration;

public interface WorkFlowFactory {
	public void setConfiguration(Configuration configuration);

	public WorkFlow getWorkFlow(String name);

	public WorkFlow getWorkFlow(long id);

	public void setWorkFlowDAO(WorkFlowDAO workFlowDAO);

	public String[] getWorkflowNames();

	public WorkFlowDAO getWorkFlowDAO();

	public void init();
}
