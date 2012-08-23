package com.samplepin.common;

import java.security.Key;

import javax.crypto.Cipher;

public class MD5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String value = "本日は晴天なり。";
		System.out.println(value);
		byte[] encode = encode(value.getBytes(), secretKey);
		System.out.println(new String(encode));
		byte[] decode = decode(encode, secretKey);
		System.out.println(new String(decode));
	}

	private static final Key secretKey = Keymaker.makeKey(128);

	public static String encode(String src, Key secretKey) {
		return new String(encode(src.getBytes(), secretKey));
	}

	public static String decode(String src, Key secretKey) {
		return new String(decode(src.getBytes(), secretKey));
	}

	public static byte[] encode(byte[] src, Key secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(Keymaker.KeyName);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 復号化
	 */
	public static byte[] decode(byte[] src, Key secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(Keymaker.KeyName);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
