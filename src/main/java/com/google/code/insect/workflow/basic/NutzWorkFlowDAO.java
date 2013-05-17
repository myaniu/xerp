package com.google.code.insect.workflow.basic;

import java.util.List;

import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.WorkFlow;
import com.google.code.insect.workflow.WorkFlowDAO;
/**
 * -----------------------------------
 * 
 * 用nutz扩展insect work 让支持数据库存储.
 * 
 * @author Tony.Xiong
 *------------------------------------
 */
public class NutzWorkFlowDAO implements WorkFlowDAO {
	//获取nutz dao
	//Dao dao = Webs.dao();

	@Override
	public WorkFlow getWorkFlow(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkFlow getWorkFlow(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveWorkFlow(WorkFlow workFlow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Token> getAllAliveToken(WorkFlow wf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAliveToken(Token token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeFinishedToken(Token token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Token getAliveToken(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(Token token, Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object setAttribute(Token token, Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeAttribute(Token token, Object key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
