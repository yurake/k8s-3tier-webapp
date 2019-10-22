package batch;


public class TaskServiceMain {

	public static void main(String[] args) {

        System.out.println("TaskServiceMain is called.");

        TaskManager timer = TaskManager.getInstance();
        timer.exec();
	}

}
