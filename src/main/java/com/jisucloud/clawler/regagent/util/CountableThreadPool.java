package com.jisucloud.clawler.regagent.util;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可计数的线程池
 */
public class CountableThreadPool implements Closeable {

	private final int threadNum;

	private AtomicInteger threadAlive = new AtomicInteger();

	private ReentrantLock reentrantLock = new ReentrantLock();

	private Condition condition = reentrantLock.newCondition();

	private ThreadPoolExecutor executorService;

	public CountableThreadPool(int threadNum) {
		this.threadNum = threadNum;
		executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		executorService.setMaximumPoolSize(threadNum * 2);
		executorService.setCorePoolSize(10);
	}

	public int getThreadAlive() {
		return executorService.getActiveCount();
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void waitIdleThread() throws Exception {
		long startTime = System.currentTimeMillis();
		while (getIdleThreadCount() <= 0) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (System.currentTimeMillis() - startTime > 1000 * 60 * 30) {
				throw new Exception("超过半小时无可用线程,threadAlive:" + getThreadAlive());
			}
		}
	}

	public void execute(final Runnable runnable) {
		if (threadAlive.get() >= threadNum) {
			try {
				reentrantLock.lock();
				while (threadAlive.get() >= threadNum) {
					try {
						condition.await();
					} catch (InterruptedException e) {
					}
				}
			} finally {
				reentrantLock.unlock();
			}
		}
		threadAlive.incrementAndGet();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} finally {
					try {
						reentrantLock.lock();
						threadAlive.decrementAndGet();
						condition.signal();
					} finally {
						reentrantLock.unlock();
					}
				}
			}
		});
	}

	public boolean isShutdown() {
		return executorService.isShutdown();
	}

	public void shutdown() {
		executorService.shutdown();
	}

	public int getIdleThreadCount() {
		return threadNum - getThreadAlive();
	}

	@Override
	public void close() {
		executorService.shutdown();
		try {
			while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
				// The thread pool has no closing
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		CountableThreadPool pool = new CountableThreadPool(10);
		pool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread.sleep(100);
		System.out.println(pool.executorService.getActiveCount());
	}
}
