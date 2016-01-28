package com.wrf.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import cn.jpush.android.api.JPushInterface;

public class BaseActivity extends Activity {
	private int currentPosition = 0;

	protected Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		if (savedInstanceState != null) {
			currentPosition = savedInstanceState.getInt("currentPosition");
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



	/**
	 * 友盟相关调用
	 * 
	 * @see Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	/**
	 * 友盟相关调用
	 * 
	 * @see Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}
}
