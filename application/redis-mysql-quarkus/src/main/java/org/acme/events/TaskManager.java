package org.acme.events;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskManager {

	static private TaskManager instace = null;
	private ExecutorService executor = null;

	private TaskManager() {
		executor = Executors.newSingleThreadExecutor();
	}

	synchronized static public TaskManager getInstance() {
		if (instace == null) {
			instace = new TaskManager();
		}
		return instace;
	}

	public void exec() {
		Runnable runnable = new Runnable() {

			public void run() {
				executor.execute(new TaskExecute());
			}
		};
		executor.execute(runnable);
	}
}