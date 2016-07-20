package secret_project;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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

	public String encrypt(String toEncrypt) throws GeneralSecurityException, UnsupportedEncodingException {
		Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
		return base64Encode(cipher.doFinal(toEncrypt.getBytes()));
	}

	public String decrypt(String toDecrypt) throws GeneralSecurityException, UnsupportedEncodingException, IOException {
		Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
		return new String(cipher.doFinal(base64Decode(toDecrypt)));
	}

	private Cipher initCipher(int mode) throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_WITH_MD5_AND_DES);
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(encryptionKey));

		Cipher pbeCipher = Cipher.getInstance(PBE_WITH_MD5_AND_DES);
		pbeCipher.init(mode, key, new PBEParameterSpec(SALT, 20));
		return pbeCipher;
	}
	
	private String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	private byte[] base64Decode(String property) throws IOException {
		return new BASE64Decoder().decodeBuffer(property);
	}

	public void createEncryptedFile(String filePath) throws GeneralSecurityException, IOException {
		byte[] fileBytes = new FileBytesReader().read(filePath);
		
		String encryptedStr = encrypt(new String(fileBytes));
		Files.write(Paths.get(filePath + ENCRYPTED_FILE_POSTFIX), encryptedStr.getBytes());
	}
}