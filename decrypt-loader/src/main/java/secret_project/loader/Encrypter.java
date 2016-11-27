package secret_project.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import secret_project.exception.UsageException;

public class Encrypter {

	private static final String PBE_WITH_MD5_AND_DES = "PBEWithMD5AndDES";
	private static final String ENCRYPTED_FILE_POSTFIX = ".encrypted";
	private static final byte[] SALT = { 
			(byte) 0xde, (byte) 0x33, 
			(byte) 0x10, (byte) 0x12, 
			(byte) 0xde, (byte) 0x33,
			(byte) 0x10, (byte) 0x12, 
	};

	public final char[] encryptionKey;

	public Encrypter(String encryptionKey) {
		this.encryptionKey = encryptionKey.toCharArray();
	}

	public String decrypt(String toDecrypt) throws GeneralSecurityException, IOException {
		Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
		return new String(cipher.doFinal(base64Decode(toDecrypt)));
	}
	
	public String encrypt(String toEncrypt) throws GeneralSecurityException {
		Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
		return base64Encode(cipher.doFinal(toEncrypt.getBytes()));
	}

	private Cipher initCipher(int mode) throws GeneralSecurityException  {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_WITH_MD5_AND_DES);
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(encryptionKey));

		Cipher pbeCipher = Cipher.getInstance(PBE_WITH_MD5_AND_DES);
		pbeCipher.init(mode, key, new PBEParameterSpec(SALT, 20));
		return pbeCipher;
	}
	
	private String base64Encode(byte[] bytes) {
		return new String(Base64.getEncoder().encode(bytes));
	}

	private byte[] base64Decode(String property) throws IOException {
		return Base64.getDecoder().decode(property);
	}

	public void createEncryptedFile(String filePath) throws UsageException {
		try {
			byte[] fileBytes = new FileBytesReader().read(filePath);
			String encryptedStr = encrypt(new String(fileBytes));
			Path file = Paths.get(filePath + ENCRYPTED_FILE_POSTFIX);
			
			Files.write(file, encryptedStr.getBytes());
		} catch (GeneralSecurityException | IOException exception) {
			throw new UsageException(exception);
		}
	}
}