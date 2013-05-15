package com.google.code.insect.workflow.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.WorkFlow;
import com.google.code.insect.workflow.WorkFlowDAO;

public class MemoryWorkFlowDAO implements WorkFlowDAO {

	private static AtomicLong id = new AtomicLong();

	public static ConcurrentMap<String, WorkFlow> workFlowsByName = new ConcurrentHashMap<String, WorkFlow>(
			20);

	public static ConcurrentMap<Long, WorkFlow> workFlowsById = new ConcurrentHashMap<Long, WorkFlow>(
			20);

	public static ConcurrentMap<Long, Token> tokens = new ConcurrentHashMap<Long, Token>(
			20);

	public static ConcurrentMap<Long, ConcurrentMap<Object, Object>> token_attributes = new ConcurrentHashMap<Long, ConcurrentMap<Object, Object>>(
			20);

	public WorkFlow getWorkFlow(long id) {
		return workFlowsById.get(id);
	}

	public Object getAttribute(Token token, Object key) {
		// TODO Auto-generated method stub
		if (token_attributes.get(token.getId()) == null)
			token_attributes.put(token.getId(), new ConcurrentHashMap());
		return token_attributes.get(token.getId()).get(key);
	}

	public WorkFlow getWorkFlow(String name) {
		// TODO Auto-generated method stub
		return workFlowsByName.get(name);
	}

	public Object removeAttribute(Token token, Object key) {
		// TODO Auto-generated method stub
		if (token_attributes.get(token.getId()) == null)
			token_attributes.put(token.getId(), new ConcurrentHashMap());
		return token_attributes.get(token.getId()).remove(key);
	}

	public void saveWorkFlow(WorkFlow workFlow) {
		if (workFlow.getName() != null)
			workFlowsByName.put(workFlow.getName(), workFlow);
		if (workFlow.getId() >= 0)
			workFlowsById.put(workFlow.getId(), workFlow);
	}

	public Object setAttribute(Token token, Object key, Object value) {
		// TODO Auto-generated method stub
		if (token_attributes.get(token.getId()) == null)
			token_attributes.put(token.getId(), new ConcurrentHashMap());
		return token_attributes.get(token.getId()).put(key, value);
	}

	public void addAliveToken(Token token) {
		this.tokens.put(token.getId(), token);
	}

	public List<Token> getAllAliveToken(WorkFlow wf) {
		return new ArrayList<Token>(this.tokens.values());
	}

	public boolean removeFinishedToken(Token token) {
		// TODO Auto-generated method stub
		this.token_attributes.remove(token.getId());
		return this.tokens.remove(token.getId(), token);
	}

	public Token getAliveToken(long id) {
		// TODO Auto-generated method stub
		return this.tokens.get(id);
	}

}
