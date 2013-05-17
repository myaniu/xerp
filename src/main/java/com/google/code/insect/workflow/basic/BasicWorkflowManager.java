package com.google.code.insect.workflow.basic;

import java.util.concurrent.atomic.AtomicLong;

import com.google.code.insect.workflow.AbstractWorkFlowManager;
import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.WorkFlow;
import com.google.code.insect.workflow.WorkFlowDAO;
import com.google.code.insect.workflow.comm.ConfigurationrException;
import com.google.code.insect.workflow.impl.TimerResource;

public class BasicWorkflowManager extends AbstractWorkFlowManager {

	private static final AtomicLong ID = new AtomicLong(0);

	public BasicWorkflowManager() {
		super();
		this.workFlowDAO = new MemoryWorkFlowDAO();
	}

	public BasicWorkflowManager(WorkFlowDAO workFlowDAO) {
		super(workFlowDAO);
	}

	protected void initFactory() {
		if (this.configuration == null)
			throw new ConfigurationrException(
					"WorkFlowManager没有设置configuration");
		this.workFlowFactory.setConfiguration(this.configuration);
		this.workFlowFactory.init();
	}

	public Token newToken(WorkFlow wf) {
		Token token = new Token(wf, this.workFlowDAO);
		token.setId(ID.incrementAndGet());
		token.setName(wf.getName());
		return token;
	}

	public void stop() {
		TimerResource.shutdownPool();
	}

}
