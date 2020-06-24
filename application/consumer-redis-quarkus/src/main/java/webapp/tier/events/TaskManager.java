package webapp.tier.events;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskManager {

	private static TaskManager instace = null;
	private ExecutorService executor = null;

	private TaskManager() {
		executor = Executors.newSingleThreadExecutor();
	}

	public static synchronized TaskManager getInstance() {
		if (instace == null) {
			instace = new TaskManager();
		}
		return instace;
	}

	public void exec() {
		Runnable runnable = new Runnable() {

			public void run() {
				try {
					executor.execute(new TaskExecute());
				} catch (Exception e) {
					executor.shutdownNow();
				}
			}
		};
		executor.execute(runnable);
	}
}