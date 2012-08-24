package com.samplepin.common;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;

public class CryptMaker {

	static final Key secretKey = Keymaker.makeKey(128);

	public static final String CHAR_SET = "UTF-8";

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

	public static String decode(String src) throws UnsupportedEncodingException {
		// return decode(src, secretKey);
		return src;
	}

	public static String decode(String src, Key secretKey)
			throws UnsupportedEncodingException {
		return new String(decode(src.getBytes(CHAR_SET), secretKey), CHAR_SET);
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

	public static String encode(String src) throws UnsupportedEncodingException {
		// return encode(src, secretKey);
		return src;
	}

	public static String encode(String src, Key secretKey)
			throws UnsupportedEncodingException {
		return new String(encode(src.getBytes(CHAR_SET), secretKey), CHAR_SET);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String value = "本日は晴天なり。";
			System.out.println(value);
			String enc = encode(value);
			System.out.println(enc);
			String dec = decode(enc);
			System.out.println(dec);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
