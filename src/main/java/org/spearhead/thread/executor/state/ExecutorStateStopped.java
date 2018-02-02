package org.spearhead.thread.executor.state;

import org.spearhead.thread.executor.AbstractExecutorState;
import org.spearhead.thread.executor.Future;

import java.util.Queue;

public class ExecutorStateStopped extends AbstractExecutorState {

	protected ExecutorStateStopped(Queue<Future> taskQueue) {
		super(taskQueue);
	}

	@Override
	public ExecutorStates getState() {
		return ExecutorStates.STOPPED;
	}
}
