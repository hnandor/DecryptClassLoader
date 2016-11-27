package secret_project.task;

import secret_project.exception.UsageException;

public class BuildTaskCommand implements TaskCommand {
	
	private static final String MESSAGE_BUILD_COMPLETED = "Build is completed.";

	@Override
	public void runTask(TaskParameter taskParameter) throws UsageException {
		System.out.println(MESSAGE_BUILD_COMPLETED);
		// we only create the ClassForEncryption.class file
	}
}

