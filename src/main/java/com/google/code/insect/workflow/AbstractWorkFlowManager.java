package com.google.code.insect.workflow;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.code.insect.workflow.basic.MemoryWorkFlowDAO;
import com.google.code.insect.workflow.comm.Common;
import com.google.code.insect.workflow.comm.FactoryException;
import com.google.code.insect.workflow.comm.InValidStateException;
import com.google.code.insect.workflow.comm.SystemException;
import com.google.code.insect.workflow.comm.TransitionType;
import com.google.code.insect.workflow.config.Configuration;
import com.google.code.insect.workflow.config.DefaultConfiguration;
import com.google.code.insect.workflow.impl.AutoResource;
import com.google.code.insect.workflow.impl.Group;
import com.google.code.insect.workflow.impl.TimerResource;
import com.google.code.insect.workflow.impl.User;
import com.google.code.insect.workflow.impl.XMLWorkflowFactory;


public abstract class AbstractWorkFlowManager implements WorkFlowManager {
	protected static final Logger logger = Logger.getAnonymousLogger();
	static {
		logger.setLevel(Level.OFF);
	}

	protected WorkFlowDAO workFlowDAO;

	protected Configuration configuration;

	protected WorkFlowFactory workFlowFactory;

	protected WorkFlowAlgorithm workFlowAlgorithm;

	public AbstractWorkFlowManager(Configuration configuration) {
		setWorkFlowAlgorithm(new DefaultWorkFlowAlgorithm());
		this.workFlowFactory = new XMLWorkflowFactory();
		this.configuration=configuration;
		// 初始化工厂
		initFactory();
		this.workFlowDAO = this.workFlowFactory.getWorkFlowDAO();
		if (this.workFlowDAO == null)
			this.workFlowDAO = new MemoryWorkFlowDAO();
	}

	public void setWorkFlowAlgorithm(WorkFlowAlgorithm workFlowAlgorithm) {
		this.workFlowAlgorithm = workFlowAlgorithm;
	}

	public AbstractWorkFlowManager(WorkFlowDAO workFlowQuery) {
		this(new DefaultConfiguration());
		this.workFlowDAO = workFlowQuery;
	}

	public AbstractWorkFlowManager() {
		this(new DefaultConfiguration());
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public final void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		// 重新初始化工厂类
		initFactory();
	}

	public WorkFlowFactory getWorkFlowFactory() {
		return workFlowFactory;
	}

	public void setWorkFlowFactory(WorkFlowFactory workFlowFactory) {
		this.workFlowFactory = workFlowFactory;
	}

	public WorkFlowDAO getWorkFlowQuery() {
		return workFlowDAO;
	}

	public void setWorkFlowQuery(WorkFlowDAO workFlowQuery) {
		this.workFlowDAO = workFlowQuery;
	}

	public void doAction(long id, Object... args) throws InValidStateException,
			SystemException {
		doAction(id, new AutoResource(), args);
	}

	public void doAction(long id) throws InValidStateException, SystemException {
		// TODO Auto-generated method stub
		doAction(id, new AutoResource());
	}

	protected void initFactory() {
		if (this.configuration == null)
			throw new FactoryException("没有设置configuration");
		this.workFlowFactory.setConfiguration(this.configuration);
		this.workFlowFactory.init();
	}

	public WorkFlow getWorkFlow(long id) {
		WorkFlow wf = this.workFlowDAO.getWorkFlow(id);
		if (wf == null) {
			wf = this.workFlowFactory.getWorkFlow(id);
			if (wf != null)
				this.workFlowDAO.saveWorkFlow(wf);
		}
		return wf;
	}

	public WorkFlow getWorkFlow(String name) {
		WorkFlow wf = this.workFlowDAO.getWorkFlow(name);
		if (wf == null) {
			wf = this.workFlowFactory.getWorkFlow(name);
			if (wf != null)
				this.workFlowDAO.saveWorkFlow(wf);
		}
		return wf;

	}

	public void saveWorkFlow(WorkFlow workFlow) {
		this.workFlowDAO.saveWorkFlow(workFlow);
	}

	protected abstract Token newToken(WorkFlow wf);

	public Token startWorkFlow(long id) {
		WorkFlow wf = this.workFlowDAO.getWorkFlow(id);
		if (wf == null) {
			wf = this.workFlowFactory.getWorkFlow(id);
			if (wf != null)
				this.workFlowDAO.saveWorkFlow(wf);
		}
		if (wf == null)
			throw new SystemException("没有找到流程(id:" + id
					+ "),可能没有设置configuration");
		Token token = newToken(wf);
		wf.addTokenToStart(token);
		this.workFlowDAO.addAliveToken(token);
		this.workFlowAlgorithm.enabledTraversing(wf);
		return token;
	}

	public Token startWorkFlow(String name) {
		WorkFlow wf = this.workFlowDAO.getWorkFlow(name);
		if (wf == null) {
			wf = this.workFlowFactory.getWorkFlow(name);
			if (wf != null)
				this.workFlowDAO.saveWorkFlow(wf);
		}
		if (wf == null)
			throw new SystemException("没有找到流程(name:" + name
					+ "),可能没有设置configuration");
		Token token = newToken(wf);
		wf.addTokenToStart(token);
		this.workFlowDAO.addAliveToken(token);
		this.workFlowAlgorithm.enabledTraversing(wf);
		return token;
	}

	protected synchronized void fireTransition(Resource resource, Token token,
			Transition transition, Object... args) {
		logger.info("resource " + resource.getId() + " exec:"
				+ transition.getName());
		synchronized (transition) {
			try {
				if (resource instanceof TimerResource)
					resource.start(transition, token, this,
							this.workFlowAlgorithm, args);
				else
					resource.start(transition, token, args);
			} catch (Exception e) {
				// e.printStackTrace();
				transition.rollBack();
				throw new SystemException("触发错误", e);
			}
		}
	}

	protected void finishToken(Resource resource, Token token, WorkFlow wf,
			Transition transition, Object... args) {
		fireTransition(resource, token, transition, args);
		if (token != null) {
			// this.workFlowDAO.removeFinishedToken(token);
			wf.getEnd().getOutputs().get(0).remove(token);
		} else
			wf.getEnd().getOutputs().get(0).removeAllTokens();
		token.setFinished(true);
	}

	public void doAction(long id, Resource resource, Object... args)
			throws InValidStateException, SystemException {
		Token token = this.workFlowDAO.getAliveToken(id);
		if (token == null)
			throw new InValidStateException("未找到案例(id:" + id + "),该案例可能已经结束");
		if (token.isFinished())
			throw new InValidStateException("本案例已经结束。");
		WorkFlow wf = token.getWorkFlow();
		List<Transition> transitions = wf.getTransitions();
		boolean hasAuto = false;
		label: for (int i = 0; i < transitions.size(); i++) {
			Transition transition = transitions.get(i);
			logger.info("begin check " + transition.getName());
			if (transition.isEnable() && transition.containTokenAtInput(token)) {
				if (transition.getType().equals(TransitionType.END)) {
					logger.info("exec end transition firing");
					finishToken(resource, token, wf, transition, args);
				} else if (transition.getResource().getId() == Common.AUTO_RESOURCE_ID
						|| transition.getResource().getId() == Common.TIMER_RESOURCE_ID) {
					// 自动执行或者定时执行
					fireTransition(transition.getResource(), token, transition,
							args);
					hasAuto = true;
				} else if (!(transition.getResource() instanceof AutoResource)
						&& (resource instanceof AutoResource)) {
					// 自动尝试执行遇到非自动资源时忽略，待客户执行
					continue;
				} else if (isUserResource(resource, transition.getResource())
						|| isGroupResource(resource, transition.getResource())) {
					// 用户执行或者用户组执行
					fireTransition(resource, token, transition, args);
				} else if (isUserBelongToGroup(resource, transition
						.getResource())) {
					// 属于用户组的用户执行
					fireTransition(resource, token, transition, args);
				} else if (isUserDefineResource(resource, transition
						.getResource())) {
					// 自定义resource执行
					fireTransition(resource, token, transition, args);
				}
				// else
				// throw new AuthorizationFailureException("resource权限不足");
				this.workFlowAlgorithm.enabledTraversing(wf);
				if (wf.getEnd().isEnable()) {
					for (Place place : wf.getEnd().getInputs()) {
						for (Token toBeEnd : place.getTokens()) {
							if (transition.getType().equals(TransitionType.END))
								finishToken(resource, toBeEnd, wf, transition,
										args);
						}
					}
				}
				continue label;
			}
			if (hasAuto) {
				hasAuto = false;
				this.workFlowAlgorithm.enabledTraversing(wf);
			}
		}
		this.workFlowAlgorithm.enabledTraversing(wf);
	}

	private boolean isUserDefineResource(Resource resource,
			Resource transitionResource) {
		return transitionResource.getClass().equals(resource.getClass())
				&& transitionResource.getId() == resource.getId();
	}

	private boolean isUserBelongToGroup(Resource resource,
			Resource transitionResource) {
		return (transitionResource instanceof Group)
				&& (resource instanceof User)
				&& (((User) resource).getGroup().getId() == transitionResource
						.getId());
	}

	private boolean isUserResource(Resource resource,
			Resource transitionResource) {
		return (transitionResource instanceof User)
				&& (resource instanceof User)
				&& transitionResource.getId() == resource.getId();
	}

	private boolean isGroupResource(Resource resource,
			Resource transitionResource) {
		return (transitionResource instanceof Group)
				&& (resource instanceof Group)
				&& transitionResource.getId() == resource.getId();
	}

	public Token getToken(long id) {
		// TODO Auto-generated method stub
		return this.workFlowDAO.getAliveToken(id);
	}

}
