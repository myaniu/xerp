package com.google.code.insect.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 类说明:
 * </p>
 * <p>
 * petri net中的place，库所，用于存储token(WorkFlow)
 * </p>
 * 
 * @author dennis
 */

public class Place implements Conditional {
	protected List<Token> tokens;

	private List<Token> tokensCopy;

	protected long id;

	protected String name;

	protected Condition condition;

	public Place() {
		this.tokens = new CopyOnWriteArrayList<Token>();
		this.tokensCopy = this.tokens;
	}

	public Place(long id) {
		this.id = id;
		this.tokens = new ArrayList<Token>();
		this.tokensCopy = this.tokens;
	}

	public Place(long id, String name) {
		this.id = id;
		this.name = name;
		this.tokens = new ArrayList<Token>();
		this.tokensCopy = this.tokens;
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

	public void remove(Token wf) {
		copyOnChange();
		this.tokens.remove(wf);
	}

	public void removeAllTokens() {
		copyOnChange();
		this.tokens.clear();
	}

	public void put(Token wf) {
		copyOnChange();
		this.tokens.add(wf);
	}

	public boolean contain(Token wf) {

		return this.tokens.contains(wf);
	}

	public boolean contain(long id) {
		boolean result = false;
		for (int i = 0; i < this.tokens.size(); i++) {
			Token token = this.tokens.get(i);
			if (token.getId() == id) {
				result = true;
				break;
			}
		}
		return result;
	}

	public synchronized List<Token> getTokens() {
		return new ArrayList<Token>(this.tokens);
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	private synchronized void copyOnChange() {
		this.tokensCopy = new ArrayList<Token>(this.tokens);
	}

	public synchronized void rollBack() {
		this.tokens = this.tokensCopy;
	}

}
