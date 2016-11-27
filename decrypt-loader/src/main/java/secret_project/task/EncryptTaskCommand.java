package secret_project.task;

import java.util.logging.Level;
import java.util.logging.Logger;

import secret_project.exception.UsageException;
import secret_project.loader.Encrypter;

public class EncryptTaskCommand implements TaskCommand {
	
	private static final Logger LOGGER = Logger.getLogger(DecryptTaskCommand.class.getName());
	private static final String MESSAGE_SUCCESSFUL_ENCRYPTION = "Successful encryption.";
	
	@Override
	public void runTask(TaskParameter taskParameter) throws UsageException {
		String encryptionKey = taskParameter.getEncryptionKey();
		String filePath = taskParameter.getFilePath();
		
		new Encrypter(encryptionKey).createEncryptedFile(filePath);
		LOGGER.log(Level.INFO, MESSAGE_SUCCESSFUL_ENCRYPTION);
	}
}
