package org.spearhead.thread.executor;

import org.spearhead.thread.executor.state.ExecutorState;

import java.util.Queue;

public abstract class AbstractExecutorState implements ExecutorState {
	protected Queue<Future> taskQueue;

	protected AbstractExecutorState(Queue<Future> taskQueue) {
		this.taskQueue = taskQueue;
	}
}
