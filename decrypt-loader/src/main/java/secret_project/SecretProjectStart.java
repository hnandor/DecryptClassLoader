package secret_project;

import secret_project.exception.UsageException;

public class SecretProjectStart {

	public static void main(String args[]) {
		String task = System.getProperty("task");
		String encryptionKey = System.getProperty("encryptionKey");
		String filePath = System.getProperty("filePath");
		try {
			TaskRunner taskRunner = new TaskRunner(task, encryptionKey, filePath);
			taskRunner.executeTask();
		} catch (UsageException e) {
			System.err.println(e);
		}
	}
}
