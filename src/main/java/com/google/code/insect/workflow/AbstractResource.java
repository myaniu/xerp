package com.google.code.insect.workflow;

import com.google.code.insect.workflow.comm.ConditionException;

public abstract class AbstractResource implements Resource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected long id;

	protected String name;

	// protected final Lock lock = new ReentrantLock();
	//
	// protected final java.util.concurrent.locks.Condition busy = lock
	// .newCondition();
	//
	// protected final java.util.concurrent.locks.Condition free = lock
	// .newCondition();
	//
	// protected transient boolean isInAction;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void start(Transition transition, Token token, Object... args)
			throws InterruptedException {
		doAction(transition, token, args);
		if (transition.getCondition() != null
				&& !transition.getCondition().check(token))
			throw new ConditionException(transition.getName()
					+ " transition没有满足触发条件");
		transition.fire(token, args);

	}

	public long getId() {
		return this.id;
	}

	public abstract void doAction(Transition transition, Token token,
			Object... args) throws InterruptedException;

	//
	// private void setFree(Transition transition, Token token, Object... args)
	// throws InterruptedException {
	// lock.lock();
	// try {
	// while (!isInAction)
	// busy.await();
	// if (transition.getCondition() != null
	// && !transition.getCondition().check(token))
	// throw new ConditionException(transition.getName()
	// + " transition没有满足触发条件");
	// transition.fire(token, args);
	// isInAction = false;
	// free.signal();
	// } catch (SystemException e) {
	// isInAction = false;
	// free.signal();
	// throw e;
	// } finally {
	// lock.unlock();
	// }
	// }

	// public final void setChanged() throws InterruptedException {
	// lock.lock();
	// try {
	// while (isInAction)
	// free.await();
	// isInAction = true;
	// busy.signal();
	// } finally {
	// lock.unlock();
	// }
	// }

}
