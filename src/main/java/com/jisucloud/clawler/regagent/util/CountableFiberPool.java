package com.jisucloud.clawler.regagent.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableRunnable;

/**
 * 可计数的线程池
 */
public class CountableFiberPool{
	
	static {
		System.setProperty("co.paralleluniverse.fibers.verifyInstrumentation", "true");
	}

	private int threadNum;

	private AtomicInteger threadAlive = new AtomicInteger();

	private ReentrantLock reentrantLock = new ReentrantLock();

	private Condition condition = reentrantLock.newCondition();

	public CountableFiberPool(int threadNum) {
		setThread(threadNum);
	}

	public int getThreadAlive() {
		return threadAlive.get();
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThread(int thread) {
		threadNum = thread;
	}
	
	public void execute(final Runnable runnable) {
		execute(runnable, null);
	}

	public void execute(final Runnable runnable, FiberListener fiberListener) {
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
		new Fiber<Void>("Caller", new SuspendableRunnable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void run() throws SuspendExecution, InterruptedException {
				try {
					if (fiberListener != null) {
						fiberListener.start(System.currentTimeMillis());
					}
					runnable.run();
				}catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						reentrantLock.lock();
						threadAlive.decrementAndGet();
						condition.signal();
					} finally {
						reentrantLock.unlock();
					}
					if (fiberListener != null) {
						fiberListener.end(System.currentTimeMillis());
					}
				}
			}
		}).start();
	}

	public int getIdleThreadCount() {
		return threadNum - getThreadAlive();
	}
	
	public void waitIdleThread() {
		while(getIdleThreadCount() <= 0) {
			try {
				Strand.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static interface FiberListener {
		
		void start(long startTimeMillis);
		
		void end(long endTimeMillis);
		
	}

	public static void main(String[] args) throws Exception {
		AtomicInteger threadAlive = new AtomicInteger();
		CountableFiberPool pool = new CountableFiberPool(1000);
		for (int i = 0; i < 100; i++) {
			pool.waitIdleThread();
			pool.execute(new Runnable() {

				@Override
				public void run() {
					long start = System.currentTimeMillis();
					System.out.println(Fiber.currentFiber().getId() +" start " + start);
					for (int j = 0; j < 50; j++) {
						try {
							Strand.sleep(10);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					threadAlive.incrementAndGet();
					System.out.println(Fiber.currentFiber().getId() +" wait time :" + (System.currentTimeMillis() - start));
				}
			});
		}
		try {
			Strand.sleep(10000 * 3);
		} catch (Exception e) {
		}
		System.out.println(threadAlive.get());
	}
}
