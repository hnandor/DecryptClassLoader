package secret_project.task;

import secret_project.exception.UsageException;

public enum Task {
	ENCRYPT(new EncryptTaskCommand()),
	DECRYPT(new DecryptTaskCommand()),
	BUILD(new BuildTaskCommand());
	
	private final TaskCommand taskCommand;
	
	private Task(TaskCommand taskCommand) {
		this.taskCommand = taskCommand;
	}

	public void run(TaskParameter taskParameter) throws UsageException {
		taskCommand.runTask(taskParameter);
	}
}
