package secret_project.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import secret_project.SecretProjectStart;
import secret_project.exception.UsageException;
import secret_project.loader.DecrypterClassLoader;

public class DecryptTaskCommand implements TaskCommand {
	
	private static final String CLASS_TO_ENCRYPT = "secret_project.loader.ClassForEncryption";
	private static final String SOME_METHOD = "someMethod";
	private static final String MESSAGE_UNSUCCESSFUL_DECRYPTION = "Unsuccessful decryption. The program stops.";
	private static final String MESSAGE_SUCCESSFUL_DECRYPTION = "Successful decryption.";

	@Override
	public void runTask(TaskParameter taskParameter) throws UsageException {
		Class<?> clazz = loadTheRequiredClass(taskParameter);
		System.out.println(MESSAGE_SUCCESSFUL_DECRYPTION);
		invokeTheStaticMethod(clazz);
	}
	
	private Class<?> loadTheRequiredClass(TaskParameter taskParameter) throws UsageException {
		try {
			String encryptionKey = taskParameter.getEncryptionKey();
			String filePath = taskParameter.getFilePath();
			
			ClassLoader parentClassLoader = SecretProjectStart.class.getClassLoader();
			DecrypterClassLoader decrypterClassLoader =
					new DecrypterClassLoader(parentClassLoader, encryptionKey, filePath);
			return decrypterClassLoader.loadClass(CLASS_TO_ENCRYPT);
		} catch (ClassNotFoundException cnfe) {
			throw new UsageException(MESSAGE_UNSUCCESSFUL_DECRYPTION);
		}
	}

	private void invokeTheStaticMethod(Class<?> clazz) throws UsageException {
		try {
			Method method = clazz.getMethod(SOME_METHOD);
			method.invoke(null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException exception) {
			throw new UsageException(MESSAGE_UNSUCCESSFUL_DECRYPTION);
		}
	}
}
