package webapp.tier.events;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskServiceMain {

	private TaskServiceMain() {
	}

	public static void main(String[] args) {

		System.out.println("TaskServiceMain is called.");

		TaskManager timer = TaskManager.getInstance();
		timer.exec();
	}

}
