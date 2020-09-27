package com.epsglobal.services.infrastructure;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.ErrorException;

@Service
public class CryptographyService {
	public String getEncryptAES(String input, String key) {
		byte[] encriptedBytes = getEncrypt(input, key, "AES");
		return Base64.getEncoder().encodeToString(encriptedBytes);
	}
	
	public String geteDecryptAES(String input, String key) {
		byte[] decryptedBytes = getDecrypt(input, key, "AES");
		return new String(Base64.getDecoder().decode(decryptedBytes));
	}
	
	public String getSHA256(String input) throws ErrorException {
		byte[] encriptedBytes = getSHA(input, "SHA-256");
		return Base64.getEncoder().encodeToString(encriptedBytes);
	}
	
	private byte[] getSHA(String input, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			return md.digest(input.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
	}
	
	private byte[] getEncrypt(String input, String key, String algorithm) {
		try {
			java.security.Key securtyKey = new SecretKeySpec(key.getBytes(), algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			
			cipher.init(Cipher.ENCRYPT_MODE, securtyKey);
			return cipher.doFinal(input.getBytes());
		}catch (InvalidKeyException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}catch (NoSuchAlgorithmException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
		catch (NoSuchPaddingException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
		catch (BadPaddingException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
		catch (IllegalBlockSizeException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
	}
	
	private byte[] getDecrypt(String input, String key, String algorithm) {
		try {
			java.security.Key securtyKey = new SecretKeySpec(key.getBytes(), algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			
			cipher.init(Cipher.DECRYPT_MODE, securtyKey);
			return cipher.doFinal(input.getBytes());
		}catch (InvalidKeyException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}catch (NoSuchAlgorithmException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
		catch (NoSuchPaddingException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
		catch (BadPaddingException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
		catch (IllegalBlockSizeException exception) {
			throw new ErrorException(CodeExceptions.ERROR, CodeExceptions.ERRORS.get(CodeExceptions.ERROR));
		}
	}
}
