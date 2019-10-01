package com.globalLogic.discovery;

public enum TimeEntity {
	SEC_0(0),SEC_1(1), SEC_2(2), SEC_3(3), SEC_5(5), SEC_10(10), SEC_15(15), SEC_30(30),MIN_0(0), MIN_1(60), MIN_2(120), MIN_3(
			180), MIN_4(240), MIN_5(300), MIN_10(10 * 60), MIN_15(15 * 60);
	private int seconds;
	private  TimeEntity(int seconds) {
		this.seconds=seconds;
		
	}
	public int getSeconds() {
		return seconds;
		
	}
	public int getMilliSeconds() {
			
		return seconds * 1000;
		
	}
	
	public int getMinutes() {
		return seconds / 60;
		
	}

}
