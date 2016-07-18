package secret_project;

import secret_project.exception.UsageException;

public class SecretProjectStart {

	public static void main(String args[]) {
		String task = System.getProperty("task");
		String encryptionKey = System.getProperty("encryptionKey");
		String filePath = System.getProperty("filePath");
		try {
			checkProperties(task, encryptionKey, filePath);
			executeTask(task, encryptionKey, filePath);
		} catch (UsageException e) {
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("Program is shut down! " + e.getMessage());
		}
	}

	public static void checkProperties(String task, String encryptionKey, String filePath) throws UsageException {
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

	private static void executeTask(String task, String encryptionKey, String filePath) throws ClassNotFoundException {
		if ("encrypt".equals(task)) {
			new Encrypter(encryptionKey).createEncryptedFile(filePath);
			System.out.println("Successful encryption.");
		} else if ("decrypt".equals(task)){
			ClassLoader parentClassLoader = SecretProjectStart.class.getClassLoader();
			DecrypterClassLoader decrypterClassLoader = new DecrypterClassLoader(parentClassLoader, encryptionKey, filePath);
			Class<?> clazz = decrypterClassLoader.loadClass("secret_project.ClassForEncryption");
			// we could use here the class object
			System.out.println("Successful decryption.");
		} else {
			System.out.println("Build is completed.");
			// we only create the ClassForEncryption.class file
		}
	}	
}
