package secret_project.task;

import java.util.logging.Level;
import java.util.logging.Logger;

import secret_project.exception.UsageException;

public class BuildTaskCommand implements TaskCommand {
	
	private static final Logger LOGGER = Logger.getLogger(BuildTaskCommand.class.getName());
	private static final String MESSAGE_BUILD_COMPLETED = "Build is completed. The class is ready for encryption.";

	@Override
	public void runTask(TaskParameter taskParameter) throws UsageException {
		LOGGER.log(Level.INFO, MESSAGE_BUILD_COMPLETED);
	}
}

