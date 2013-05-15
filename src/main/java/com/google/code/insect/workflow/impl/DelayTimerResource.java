package com.google.code.insect.workflow.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;


public class DelayTimerResource extends TimerResource {
	private long delay;

	private TimeUnit timeUnit;

	public DelayTimerResource(long delay_seconds, TimeUnit timeUnit) {
		super();
		this.delay = delay_seconds;
		this.timeUnit = timeUnit;
	}

	public DelayTimerResource(int pool_size, long delay_seconds,
			TimeUnit timeUnit) {
		if (pool_size <= 0)
			throw new IllegalArgumentException("线程池大小仅能为正整数");
		this.pool_size = pool_size;
		if (scheduledExecutorService == null)
			scheduledExecutorService = Executors
					.newScheduledThreadPool(this.pool_size);
		this.delay = delay_seconds;
		this.timeUnit = timeUnit;
	}

	@Override
	public void doAction(Transition transition, Token token, Object... args)
			throws InterruptedException {
		scheduledExecutorService.schedule(new ChangeRunner(transition, token,
				args), this.delay, this.timeUnit);

	}
}
