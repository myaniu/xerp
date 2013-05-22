package com.google.code.insect.workflow;

import java.util.List;

public interface WorkFlowDAO {
	public WorkFlow getWorkFlow(long id);

	public WorkFlow getWorkFlow(String name);

	public void saveWorkFlow(WorkFlow workFlow);

	// // 用于Process的token的crud操作
	public List<Token> getAllAliveToken(WorkFlow wf);

	public void addAliveToken(Token token);

	public boolean removeFinishedToken(Token token);
	
	public Token getAliveToken(long id);

	//
	// 用于Token属性的crud操作
	public Object getAttribute(Token token, Object key);

	public Object setAttribute(Token token, Object key, Object value);

	public Object removeAttribute(Token token, Object key);
	//
	// // 用于Place的存储token的crud操作
	// public boolean removeTokenForPlace(Token token, Place place);
	//
	// public void putTokenForPlace(Token token, Place place);
	//
	// public List<Token> queryTokensForPlace(Place place);
	//
	// public boolean containToken(Token token, Place place);
	//
	// // 用于Transition的输入输出库所的crud操作
	// public boolean addInputPlace(Transition transition, Place place);
	//
	// public boolean removeInputPlace(Transition transition, Place place);
	//
	// public boolean containInputPlace(Transition transition, Place place);
	//
	// public List<Place> getAllInputPlace(Transition transition);
	//
	// public boolean addOutputPlace(Transition transition, Place place);
	//
	// public boolean removeOutputPlace(Transition transition, Place place);
	//
	// public boolean containOutputPlace(Transition transition, Place place);
	//
	// public List<Place> getAllOutputPlace(Transition transition);

}
