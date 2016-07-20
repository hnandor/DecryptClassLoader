package secret_project;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;

import secret_project.exception.UsageException;

public class TaskRunner {
	
	private final String task;
	private final String encryptionKey;
	private final String filePath;
	
	public TaskRunner(String task, String encryptionKey, String filePath) {
		this.task = task;
		this.encryptionKey = encryptionKey;
		this.filePath = filePath;
	}

	public void executeTask() throws UsageException {
		try {
			checkProperties();
			runTask();
		} catch (ClassNotFoundException | GeneralSecurityException | IOException e) {
			throw new UsageException("Program cannot be continued! " + e.getMessage());
		}
	}
	
	public void checkProperties() throws UsageException {
		if (task == null || (!"encrypt".equals(task) && !"decrypt".equals(task) && !"build".equals(task))) {
			throw new UsageException("Property 'task' should be set: build/encrypt/decrypt");
		}
		if (!"build".equals(task)) {
			if (task == null || encryptionKey.isEmpty()) {
				throw new UsageException("Property 'encryptionKey' should be set.");
			}
			if (filePath == null || filePath.isEmpty()) {
				throw new UsageException("Property 'filePath' should be set.");
			}
		}
	}

	private void runTask() throws GeneralSecurityException, IOException, ClassNotFoundException {
		if ("encrypt".equals(task)) {
			new Encrypter(encryptionKey).createEncryptedFile(filePath);
			System.out.println("Successful encryption.");
		} else if ("decrypt".equals(task)) {
			Class<?> clazz = loadTheRequiredClass();
			System.out.println("Successful decryption.");
			invokeTheStaticMethod(clazz);
		} else {
			System.out.println("Build is completed.");
			// we only create the ClassForEncryption.class file
		}
	}

	private Class<?> loadTheRequiredClass() throws ClassNotFoundException {
		ClassLoader parentClassLoader = SecretProjectStart.class.getClassLoader();
		DecrypterClassLoader decrypterClassLoader = new DecrypterClassLoader(parentClassLoader, encryptionKey, filePath);
		return decrypterClassLoader.loadClass("secret_project.ClassForEncryption");
	}

	private void invokeTheStaticMethod(Class<?> clazz) {
		try {
			Method method = clazz.getMethod("someMethod");
			method.invoke(null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
