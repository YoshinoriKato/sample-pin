package com.samplepin.servlet.util;

public class Snipet2 {

	public static void main(String[] args) {
		String text = "*";
		if (text.matches("[ -/:-@\\[-\\`\\{-\\~]+")) {
			System.out.println(text);
		}
		System.out.println("bye.");
	}
}
