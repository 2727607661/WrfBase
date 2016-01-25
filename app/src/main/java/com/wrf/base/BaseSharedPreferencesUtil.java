package com.wrf.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BaseSharedPreferencesUtil {

	protected static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static int getConfig(Context context, String key, int defValue) {
		return getSharedPreferences(context).getInt(key, defValue);
	}

	public static long getConfig(Context context, String key, long defValue) {
		return getSharedPreferences(context).getLong(key, defValue);
	}

	public static boolean getConfig(Context context, String key, boolean defValue) {
		return getSharedPreferences(context).getBoolean(key, defValue);
	}

	public static String getConfig(Context context, String key, String defValue) {
		return getSharedPreferences(context).getString(key, defValue);
	}

	public static boolean setConfig(Context context, String key, int value) {
		return getSharedPreferences(context).edit().putInt(key, value).commit();
	}

	public boolean setConfig(Context context, String key, long value) {
		return getSharedPreferences(context).edit().putLong(key, value)
				.commit();
	}

	public static boolean setConfig(Context context, String key, boolean value) {
		return getSharedPreferences(context).edit().putBoolean(key, value)
				.commit();
	}

	public static boolean setConfig(Context context, String key, String value) {
		return getSharedPreferences(context).edit().putString(key, value)
				.commit();
	}
}
