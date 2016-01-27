package com.wrf.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class BaseActivity extends Activity {
	private static int mScreenWidth, mScreenHeight;
	private int currentPosition = 0;

	protected Context context;
	public static boolean isForeground = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		if (savedInstanceState != null) {
			currentPosition = savedInstanceState.getInt("currentPosition");
		}
		if (mScreenWidth == 0 || mScreenHeight == 0) {
			DisplayMetrics metrics = getResources().getDisplayMetrics();
			mScreenWidth = metrics.widthPixels;
			mScreenHeight = metrics.heightPixels;
		}
	}

	public Activity getActivity() {
		return this;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("currentPosition", currentPosition);
	}

	@Override
	public void finish() {
		super.finish();
	}



	public void onBackClick(View v) {
		finish();
	}

	/**
	 * 
	 * @return
	 */
	public int getScreenWidth() {
		return mScreenWidth;
	}

	/**
	 * 
	 * @return
	 */
	public int getScreenHeight() {
		return mScreenHeight;
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T $(View view, int ResId) {
		return (T) view.findViewById(ResId);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T $(int ResId) {
		return (T) this.findViewById(ResId);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T $(Activity activity, int ResId) {
		return (T) activity.findViewById(ResId);
	}

	/** 传参 跳转页面 */
	protected void startActivity(Class<? extends  Activity> c,
			String key, String value) {
		Intent intent = new Intent(context, c);
		intent.putExtra(key, value);
		context.startActivity(intent);
	}

	/** 传参 跳转页面 */
	protected void startActivity(Class<? extends Activity> c, String key,
			boolean value) {
		Intent intent = new Intent(context, c);
		intent.putExtra(key, value);
		context.startActivity(intent);
	}


	protected void startActivity(Class<? extends Activity> c) {
		Intent intent = new Intent(context, c);
		context.startActivity(intent);
	}

	/**
	 * 友盟相关调用
	 * 
	 * @see Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		isForeground = false;
	}

	/**
	 * 友盟相关调用
	 * 
	 * @see Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		isForeground = true;
	}
}
