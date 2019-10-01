package com.globalLogic.discovery;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeManager {
	private static String DATE_FORMAT_NOW = "yyyy-MM-dd";
	private static String TIME_FORMAT_NOW = "HH:mm:ss";
	public static String DATE_TIME_AMPM__FORMAT = "dd-MMM-yyyy hh:mm:ss a";
	public static String dd_MMM_yy_FORMAT = "dd-MMM-yy";

	public static void waitInMinutes(int min) {
		try {
			Thread.sleep(min * 60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void waitInSeconds(int seconds) {
		int millisec = seconds * 1000;
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public static void waitInMilliSeconds(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	public static String timeNow() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public static String dateNow() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public static String dateNow(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	public static String dateTimeNow(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());

	}

	public static String getDateAndTime() {
		return dateNow() + "  " + timeNow();
	}

		public static int convertToMilliSeconds(int seconds) {
		return seconds * 1000;
	}


}
