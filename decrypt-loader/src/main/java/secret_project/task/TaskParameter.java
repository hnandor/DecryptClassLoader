package secret_project.task;

import secret_project.exception.UsageException;

public class TaskParameter {
	
	private static final String MESSAGE_MISSING_PROPERTY_TASK = "Property 'task' should be set to build/encrypt/decrypt.";
	private static final String MESSAGE_MISSING_PROPERTY_PATH = "Property 'filePath' should be set.";
	private static final String MESSAGE_MISSING_PROPERTY_KEY = "Property 'encryptionKey' should be set.";
	
	private final String taskName;
	private final String encryptionKey;
	private final String filePath;
	
	public TaskParameter(String taskName, String encryptionKey, String filePath) {
		this.taskName = taskName;
		this.encryptionKey = encryptionKey;
		this.filePath = filePath;
	}
	
	public Task getTask() throws UsageException {
		Task task = getTask(taskName);
		checkParameters(task);
		return task;
	}
	
	public Task getTask(String taskStr) throws UsageException {
		try {
			if (taskStr != null) {
				return Task.valueOf(taskStr.toUpperCase());
			}
		} catch (IllegalArgumentException iae) {
			// no enum value exists
		}
		throw new UsageException(MESSAGE_MISSING_PROPERTY_TASK);	
	}
	
	private void checkParameters(Task task) throws UsageException { 
		if (!Task.BUILD.equals(task)) {
			if (encryptionKey == null || encryptionKey.isEmpty()) {
				throw new UsageException(MESSAGE_MISSING_PROPERTY_KEY);
			}
			if (filePath == null || filePath.isEmpty()) {
				throw new UsageException(MESSAGE_MISSING_PROPERTY_PATH);
			}
		}
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public String getFilePath() {
		return filePath;
	}
}
