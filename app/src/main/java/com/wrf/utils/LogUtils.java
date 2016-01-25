package com.wrf.utils;

import android.util.Log;

public class LogUtils {
	public static boolean isLog = true;
	public static String TAG = "LogUtils";
	public static void log(String msg) {
		log(TAG, msg);
	}
	public static void log(int msg) {
		log(TAG, msg + "");
	}

	public static void log(String tag, String msg) {
		if (isLog) {
			Log.i(tag, msg);
		}
	}

	public static void log(Exception ex) {
		ex.printStackTrace();
	}

	public static void log(String format, Object... args) {
		log(String.format(format, args));
	}
}
