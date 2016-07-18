package secret_project;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encrypter {

	private static final String UTF_8 = "UTF-8";
	private static final String PBE_WITH_MD5_AND_DES = "PBEWithMD5AndDES";
	private static final byte[] SALT = { (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, (byte) 0xde, (byte) 0x33,
			(byte) 0x10, (byte) 0x12, };

	public final char[] encryptionKey;

	public Encrypter(String encryptionKey) {
		this.encryptionKey = encryptionKey.toCharArray();
	}

	public String encrypt(String toEncrypt) throws GeneralSecurityException, UnsupportedEncodingException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_WITH_MD5_AND_DES);
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(encryptionKey));
		Cipher pbeCipher = Cipher.getInstance(PBE_WITH_MD5_AND_DES);
		pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
		return base64Encode(pbeCipher.doFinal(toEncrypt.getBytes()));
	}

	public String decrypt(String toDecrypt) throws GeneralSecurityException, UnsupportedEncodingException, IOException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_WITH_MD5_AND_DES);
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(encryptionKey));
		Cipher pbeCipher = Cipher.getInstance(PBE_WITH_MD5_AND_DES);
		pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
		return new String(pbeCipher.doFinal(base64Decode(toDecrypt)));
	}

	public String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	public byte[] base64Decode(String property) throws IOException {
		return new BASE64Decoder().decodeBuffer(property);
	}
	
	public void createEncryptedFile(String filePath) {
		byte[] fileBytes = new FileBytesReader().read(filePath);
		try {
			String encryptedStr = encrypt(new String(fileBytes));
			Files.write(Paths.get(filePath + ".encrypted"), encryptedStr.getBytes());
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
	}
}