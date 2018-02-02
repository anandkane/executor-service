package org.spearhead.thread.executor;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.Callable;

public class ExecutorTest {
	private static Logger logger = Logger.getLogger(ExecutorTest.class);

	@Test
	public void test1Simple() throws InterruptedException {
		Callable<Integer> add = () -> {
			logger.info("Invoked addition callable");
			return 3 + 2;
		};
		Executor executor = new SimpleExecutor();
		executor.start();
		Future<Integer> future = executor.submit(add);
		Integer result = future.get();
		logger.info("Result = " + result);

		Thread.sleep(30000);
		executor.shutDown();
	}

	@Test
	public void test2LongRunning() throws InterruptedException {
		Callable<Integer> one = () -> {
			long start = System.currentTimeMillis();

			int count = 0;
			for (int i = 0; i < 100000; i++) {
				// Something meaningless
				if (i % 100 == 0) {
					logger.trace("Tracing...");
					count += 50;
				}
			}

			logger.info("Execution time " + (System.currentTimeMillis() - start));
			return count;
		};

		Callable<Integer> two = () -> {
			long start = System.currentTimeMillis();

			int count = 0;
			for (int i = 0; i < 100000; i++) {
				// Something meaningless
				if (i % 100 == 0) {
					logger.trace("Tracing...");
					count -= 50;
				}
			}

			logger.info("Execution time " + (System.currentTimeMillis() - start));
			return count;
		};

		Executor executor = new SimpleExecutor();
		executor.start();

		Future<Integer> future1 = executor.submit(one);
		Future<Integer> future2 = executor.submit(two);

		System.out.println(future1.get());
		System.out.println(future2.get());

		Thread.sleep(10000);
		executor.shutDown();
	}

	@Test
	public void test6LongRunning() throws InterruptedException {

		Callable<Integer> one = () -> {
			long start = System.currentTimeMillis();

			int count = 0;
			for (int i = 0; i < 100000; i++) {
				// Something meaningless
				if (i % 100 == 0) {
					logger.trace("Tracing...");
					count += 50;
				}
			}

			logger.info("Execution time " + (System.currentTimeMillis() - start));
			return count;
		};

		Executor executor = new SimpleExecutor();
		executor.start();

		Future[] futures = new Future[6];
		for (int i = 0; i < 6; i++) {
			futures[i] = executor.submit(one);
		}

		boolean finished = false;
		while (!finished) {
			for (Future future : futures) {
				finished &= future.isCompleted();
			}
		}

		Thread.sleep(30000);
	}
}
