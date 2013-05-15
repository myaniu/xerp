package com.google.code.insect.workflow;

import com.google.code.insect.workflow.comm.InValidStateException;
import com.google.code.insect.workflow.comm.SystemException;
import com.google.code.insect.workflow.config.Configuration;

public interface WorkFlowManager {
	public void doAction(long id, Resource resource, Object... args)
			throws InValidStateException, SystemException;

	public void doAction(long id, Object... args) throws InValidStateException,
			SystemException;

	public void doAction(long id) throws InValidStateException, SystemException;

	public Token startWorkFlow(long id);

	public Token startWorkFlow(String name);

	public void setConfiguration(Configuration configuration);

	public WorkFlow getWorkFlow(long id);

	public WorkFlow getWorkFlow(String name);

	public Token getToken(long id);

	public void saveWorkFlow(WorkFlow workFlow);

	public void setWorkFlowAlgorithm(WorkFlowAlgorithm workFlowAlgorithm);

	public void stop();

}
