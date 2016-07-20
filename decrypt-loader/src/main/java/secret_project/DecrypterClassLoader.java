package secret_project;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class DecrypterClassLoader extends ClassLoader {

	private static final String ENCRYPTED_CLASS_NAME = "secret_project.ClassForEncryption";
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
		byte[] fileBytes = new FileBytesReader().read(filePath);
		Encrypter encrypter = new Encrypter(encryptionKey);

		try {
			String decrypted = encrypter.decrypt(new String(fileBytes));
			byte[] decryptedBytes = decrypted.getBytes();

			Class<?> clazz = defineClass(name, decryptedBytes, 0, decryptedBytes.length);
			resolveClass(clazz);
			return clazz;
		} catch (GeneralSecurityException | IOException e) {
			throw new ClassNotFoundException("Unsuccessful decryption!", e);
		}
	}
}