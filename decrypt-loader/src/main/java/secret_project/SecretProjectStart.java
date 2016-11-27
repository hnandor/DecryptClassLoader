package secret_project;

import java.util.logging.Level;
import java.util.logging.Logger;

import secret_project.exception.UsageException;
import secret_project.task.Task;
import secret_project.task.TaskParameter;

public class SecretProjectStart {
	
	private static final Logger LOGGER = Logger.getLogger(SecretProjectStart.class.getName());

	public static void main(String args[]) {
		try {
			new SecretProjectStart().runTask();
		} catch (UsageException exception) {
			LOGGER.log(Level.INFO, exception.toString());
		}
	}

	private void runTask() throws UsageException {
		TaskParameter taskParameter = createTaskParameter();
		Task task = taskParameter.getTask();
		task.run(taskParameter);
	}

	private TaskParameter createTaskParameter() {
		String taskName = System.getProperty("task");
		String encryptionKey = System.getProperty("encryptionKey");
		String filePath = System.getProperty("filePath");
		
		return new TaskParameter(taskName, encryptionKey, filePath);
	}
}
