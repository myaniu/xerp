package com.google.code.insect.workflow.impl;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.code.insect.workflow.Token;
import com.google.code.insect.workflow.Transition;
import com.google.code.insect.workflow.comm.InvalidTimerResourceException;
import com.google.code.insect.workflow.util.DateTimeUtil;


public class FixTimerResource extends TimerResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String at;

	private boolean auto_next;

	public FixTimerResource(String at) {
		super();
		this.at = at;
		this.auto_next = true;
	}

	public FixTimerResource(String at, boolean auto_next) {
		super();
		this.at = at;
		this.auto_next = auto_next;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

	public boolean isAutoNext() {
		return auto_next;
	}

	public void setAutoNext(boolean auto_next) {
		this.auto_next = auto_next;
	}

	public FixTimerResource(int pool_size, String at, boolean repeat) {
		if (pool_size <= 0)
			throw new IllegalArgumentException("线程池大小仅能为正整数");
		this.pool_size = pool_size;
		scheduledExecutorService = Executors
				.newScheduledThreadPool(this.pool_size);
		this.at = at;
	}

	@Override
	public void doAction(Transition transition, Token token, Object... args)
			throws InterruptedException {
		Date date = DateTimeUtil.parseDateTime(at, "yyyy-MM-dd HH:mm:ss");
		if (date == null) {
			date = DateTimeUtil.parseDateTime((DateTimeUtil
					.getDateTime("yyyy-MM-dd")
					+ " " + at), "yyyy-MM-dd HH:mm:ss");
		}

		long delay = date.getTime() - System.currentTimeMillis();
		if (delay < 0 && auto_next) {
			at = DateTimeUtil.getDateTime(date, "yyyy-MM-dd HH:mm:ss");
			date = DateTimeUtil.parseDateTime((DateTimeUtil.getBeforDay(-1,
					"yyyy-MM-dd") + at.split(" ")[1]), "yyyy-MM-dd HH:mm:ss");
			delay = date.getTime() - System.currentTimeMillis();
		} else if (delay < 0 && !auto_next)
			throw new InvalidTimerResourceException("指定时间已经过期");
		scheduledExecutorService.schedule(new ChangeRunner(transition, token,
				args), delay, TimeUnit.MILLISECONDS);
	}
}
