package com.google.code.insect.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.code.insect.workflow.comm.SystemException;
import com.google.code.insect.workflow.comm.TransitionType;
import com.google.code.insect.workflow.impl.AutoResource;


/**
 * <p>
 * 类说明:
 * </p>
 * <p>
 * petri net中的transition（变迁）
 * 
 * @author dennis
 * 
 */
public class Transition implements Conditional {

	protected long id;

	protected String name;

	protected WorkFlowDAO workFlowDAO;

	protected transient boolean enable; // 标志变迁是否已经就绪

	protected ActivityHandler activityHandler;

	protected Condition condition;

	protected List<Place> inputs;

	protected List<Place> outputs;

	protected Resource resource;

	protected TransitionType type;

	protected List<Place> inputsCopy;

	protected List<Place> outputsCopy;

	// protected final Lock lock = new ReentrantLock();
	//
	// protected final java.util.concurrent.locks.Condition free = lock
	// .newCondition();
	//
	// protected final java.util.concurrent.locks.Condition busy = lock
	// .newCondition();

	public Transition() {
		this.inputs = new CopyOnWriteArrayList<Place>();
		this.outputs = new CopyOnWriteArrayList<Place>();
		this.resource = new AutoResource();
		this.enable = false;
		this.type = TransitionType.NORMAL;

	}

	public Transition(long id) {
		this.id = id;
		this.inputs = new ArrayList<Place>();
		this.outputs = new ArrayList<Place>();
		this.resource = new AutoResource();
		this.enable = false;
		this.type = TransitionType.NORMAL;
	}

	public Transition(long id, String name) {
		this.id = id;
		this.name = name;
		this.inputs = new ArrayList<Place>();
		this.outputs = new ArrayList<Place>();
		this.resource = new AutoResource();
		this.enable = false;
		this.type = TransitionType.NORMAL;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public String getName() {
		return name;
	}

	public WorkFlowDAO getWorkFlowDAO() {
		return workFlowDAO;
	}

	public void setWorkFlowDAO(WorkFlowDAO workFlowDAO) {
		this.workFlowDAO = workFlowDAO;
	}

	public void setOutputs(List<Place> outputs) {
		this.outputs = outputs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Place> getInputs() {
		return this.inputs;
	}

	public boolean addInputPlace(Place place) {
		return this.inputs.add(place);
	}

	public boolean removeInputPlace(Place place) {
		return this.inputs.remove(place);
	}

	public boolean containInputPlace(Place place) {
		return this.inputs.contains(place);
	}

	public void setInputs(List<Place> inputs) {
		this.inputs = inputs;
	}

	public boolean containTokenAtInput(Token token) {
		boolean result = false;
		for (int i = 0; i < this.inputs.size(); i++) {
			Place place = this.inputs.get(i);
			if (place.contain(token.getId())) {
				result = true;
				break;
			}
		}
		return result;
	}

	public List<Place> getOutputs() {
		return this.outputs;
	}

	public boolean addOutputPlace(Place place) {
		return this.outputs.add(place);
	}

	public boolean removeOutputPlace(Place place) {
		return this.outputs.remove(place);
	}

	public boolean containOutputPlace(Place place) {
		return this.outputs.contains(place);
	}

	public TransitionType getType() {
		return this.type;
	}

	public void setType(TransitionType type) {
		this.type = type;
	}

	public ActivityHandler getActivityHandler() {
		return activityHandler;
	}

	public void setActivityHandler(ActivityHandler activityHandler) {
		this.activityHandler = activityHandler;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void fire(Token token, Object... args) {
		removeTokenFromInputs(token);
		addTokenToOutputs(token);
		invokeHandler(token, args);
	}

	public void invokeHandler(Token token, Object... args) {
		if (this.activityHandler != null)
			this.activityHandler.invoke(token, args);
	}

	public void addTokenToOutputs(Token token) {
		boolean isChoosed = false;
		for (Place output : this.outputs) {
			if (output.getCondition() != null
					&& output.getCondition().check(token)) {
				output.put(token);
				isChoosed = true;
			} else if (output.getCondition() == null) {
				output.put(token);
				isChoosed = true;
			}

		}
		if (!isChoosed)
			throw new SystemException("选择条件全部未满足，action将回滚");
	}

	public void removeTokenFromInputs(Token token) {
		for (Place input : this.inputs) {
			input.remove(token);
		}
	}

	public void rollBack() {
		for (Place input : this.inputs) {
			input.rollBack();
		}
		// for (Place output : this.outputs) {
		// output.rollBack();
		// }
	}

	public Set<Place> getAllPlace() {
		Set<Place> result = new HashSet<Place>(this.inputs.size()
				+ this.outputs.size());
		result.addAll(this.inputs);
		result.addAll(this.outputs);
		return result;
	}
}
