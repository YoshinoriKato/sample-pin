package com.samplepin;

public enum Category {

	ETC(0), NEWS(10), IT(20), BUSINESS(30), ENTERTAINMENT(40), FOOD(50);

	Category(int num) {
		this.num = num;
	}

	int num;

	int get() {
		return num;
	}
}
