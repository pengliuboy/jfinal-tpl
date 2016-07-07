package com.lp.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CommonUtils {
	
	public static final String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static final String decryptAES(String input, String key) {
		byte[] output = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.getDecoder().decode(input.getBytes()));
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
		
		return new String(output);
	}
	
	public static String encryptSHA1(String str) {
		return encrypt(str, "SHA-1");
	}
	
	public static String encryptMD5(String str) {
		return encrypt(str, "MD5");
	}
	
	public static String encrypt(String str, String mdInstance) {
		StringBuffer strResult = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance(mdInstance);
			md.update(str.getBytes());
			byte[] encryptedBytes = md.digest();

			String stmp;
			for (int n = 0; n < encryptedBytes.length; n++) {
				stmp = (Integer.toHexString(encryptedBytes[n] & 0XFF));
				if (stmp.length() == 1) {
					strResult.append("0");
					strResult.append(stmp);
				} else {
					strResult.append(stmp);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strResult.toString();
	}
}
