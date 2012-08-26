package com.samplepin.common;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

class Keymaker {

	public static String KeyName = "AES";

	public static Key makeKey(int key_bits) {
		// バイト列
		byte[] key = new byte[key_bits / 8];

		// バイト列の内容（秘密鍵の値）はプログラマーが決める
		for (int i = 0; i < key.length; i++) {
			key[i] = (byte) (i + 1);
		}

		return new SecretKeySpec(key, KeyName);
	}
}