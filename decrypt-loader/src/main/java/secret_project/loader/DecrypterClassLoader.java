package secret_project.loader;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class DecrypterClassLoader extends ClassLoader {

	private static final String ENCRYPTED_CLASS_NAME = "secret_project.loader.ClassForEncryption";
	private static final String MESSAGE_UNSUCCESSFUL_DECRYPTION = "Unsuccessful decryption. The program stops.";
	
	private final String encryptionKey;
	private final String filePath;

	public DecrypterClassLoader(ClassLoader parent, String encryptionKey, String filePath) {
		super(parent);
		this.encryptionKey = encryptionKey;
		this.filePath = filePath;
	}
	
	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		if (className.startsWith(ENCRYPTED_CLASS_NAME)) {
			return loadEncryptedClass(className);
		}
		return super.loadClass(className);
	}

	private Class<?> loadEncryptedClass(String name) throws ClassNotFoundException {
		try {
			Class<?> clazz = defineClass(name);
			resolveClass(clazz);
			return clazz;
		} catch (GeneralSecurityException | IOException exception) {
			throw new ClassNotFoundException(MESSAGE_UNSUCCESSFUL_DECRYPTION);
		}
	}

	private Class<?> defineClass(String name) throws GeneralSecurityException, IOException {
		byte[] fileBytes = new FileBytesReader().read(filePath);
		String decrypted = new Encrypter(encryptionKey).decrypt(new String(fileBytes));
		byte[] decryptedBytes = decrypted.getBytes();

		return defineClass(name, decryptedBytes, 0, decryptedBytes.length);
	}
}