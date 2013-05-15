package com.google.code.insect.workflow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.code.insect.workflow.comm.SystemException;
import com.google.code.insect.workflow.comm.TransitionType;


/**
 * <p>
 * 类说明：用于存储流程信息
 * </p>
 * 
 * @author dennis
 * 
 */
public class WorkFlow {
	private long id;

	private String name;

	private WorkFlowDAO workFlowDAO;

	private Transition start, end;

	private List<Transition> transitions;

	public WorkFlow() {
		this.transitions = new ArrayList<Transition>();
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

	public List<Transition> getTransitions() {
		return new ArrayList<Transition>(transitions);
	}

	public List<Transition> getTransitionsWithoutStartEnd() {
		List<Transition> result = new ArrayList<Transition>(this.transitions
				.size() - 2);
		for (Transition transition : this.transitions) {
			if (!transition.getType().equals(TransitionType.START)
					|| !transition.getType().equals(TransitionType.END))
				result.add(transition);
		}
		return result;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	public boolean addTransition(Transition transition) {
		synchronized (transitions) {
			return this.transitions.add(transition);
		}
	}

	public boolean removeTransition(Transition transition) {
		synchronized (transitions) {
			return this.transitions.remove(transition);
		}
	}

	public boolean containsTransition(long id) {
		synchronized (transitions) {
			boolean result = false;
			for (int i = 0; i < this.transitions.size(); i++) {
				Transition transition = this.transitions.get(i);
				if (transition.getId() == id) {
					result = true;
					break;
				}
			}
			return result;
		}

	}

	public List<Token> getAllAliveTokens() {
		List<Token> list = new ArrayList<Token>();
		for (Transition transition : this.transitions) {
			for (Place place : transition.getOutputs())
				list.addAll(place.getTokens());
			for (Place place : transition.getInputs())
				list.addAll(place.getTokens());
		}
		return list;
	}

	public Set<Place> getAllPlaces() {
		Set<Place> set = new HashSet<Place>();
		for (Transition transition : this.transitions) {
			set.addAll(transition.getInputs());
			set.addAll(transition.getOutputs());
		}
		return set;
	}

	public void addToken(Token token) {
		addTokenToStart(token);
	}

	public synchronized void addTokenToStart(Token token) {
		this.start.getInputs().get(0).put(token);
	}

	public Transition getStart() {
		return start;
	}

	public void setStart(Transition start) {
		if (this.start != null)
			throw new SystemException("一个流程只能有一个start节点");
		this.start = start;
	}

	public Transition getEnd() {
		return end;
	}

	public void setEnd(Transition end) {
		if (this.end != null)
			throw new SystemException("一个流程只能有一个end节点");
		this.end = end;
	}

	public Place getPlace(long id) {
		for (Transition transition : this.transitions) {
			for (Place place : transition.getAllPlace()) {
				if (place.getId() == id)
					return place;
			}
		}
		return null;
	}

	public Transition getTransition(long id) {
		for (Transition transition : this.transitions) {
			if (transition.getId() == id)
				return transition;
		}
		return null;
	}

}
