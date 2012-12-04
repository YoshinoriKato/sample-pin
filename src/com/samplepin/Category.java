package com.samplepin;

public enum Category {

	ETC(0), NEWS(10), IT(20), BUSINESS(30), ENTERTAINMENT(40), FOOD(50);

	int num;

	Category(int num) {
		this.num = num;
	}

	int get() {
		return this.num;
	}
}
