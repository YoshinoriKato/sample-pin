package com.samplepin.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

	static String ALPABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	static String NUMBER = "0123456789_";

	static String MARK = "#$@";

	static String PASSWORD_LETTER = ALPABET + NUMBER + MARK;

	static int DEFAULT_LEN = 12;

	static int FIRST_CHAR = 1;

	static final AtomicLong SEED = new AtomicLong(System.nanoTime());

	public static String generatedIdString(long seed, String range, int length) {
		Random r = new Random(seed);
		StringBuilder builder = new StringBuilder();

		int len = range.length();
		char[] array = range.toCharArray();

		for (int i = 0; i < length; i++) {
			builder.append(array[r.nextInt(len)]);
		}

		return builder.toString();
	}

	public static String hexString(byte[] hash) {
		StringBuilder s = new StringBuilder();
		int size = hash.length;
		for (int i = 0; i < size; i++) {
			int n = hash[i];
			if (n < 0) {
				n += 256;
			}
			String hex = Integer.toHexString(n);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			s.append(hex);
		}
		return s.toString();
	}

	public static void main(String[] args) {
		System.out.println(randomAscii(10));
		System.out.println(randomAscii(10));
		System.out.println(randomAscii(10));
		System.out.println(randomAscii(10));
		System.out.println(randomAscii(10));
		System.out.println("bye.");
	}

	public static String random(int count) {
		return generatedIdString(SEED.getAndIncrement(), NUMBER, count);
	}

	public static String randomAlphabetic(int count) {
		return generatedIdString(SEED.getAndIncrement(), ALPABET, count);
	}

	public static String randomAlphanumeric(int count) {
		return generatedIdString(SEED.getAndIncrement(), ALPABET + NUMBER,
				count);
	}

	public static String randomAscii(int count) {
		StringBuilder s = new StringBuilder();
		Random r = new Random(System.nanoTime());
		int min = '!';
		int max = '~';
		int seed = max - min;
		for (int i = 0; i < count; i++) {
			s.append((char) (min + r.nextInt(seed)));
		}
		return s.toString();
	}

	public static String randomNumeric(int count) {
		return generatedIdString(SEED.getAndIncrement(), NUMBER, count);
	}

	public static String randowMD5(String value)
			throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(value.getBytes());
		byte[] hash = md5.digest();
		return hexString(hash);
	}

	public static String randowUUID() {
		return UUID.randomUUID().toString();
	}
}
