package com.wrf.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class BaseFragmentActivity extends FragmentActivity {
	private static int mScreenWidth, mScreenHeight;

	private Fragment mCurrentFragment;
	private int currentPosition = 0;
	private List<Class<? extends Fragment>> fragments = new ArrayList<Class<? extends Fragment>>();
	private int containerId;
	protected Context context;
	private boolean isCnzz = false;// 是否已经统计

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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("currentPosition", currentPosition);
	}

	/**
	 * 
	 * @return 获取屏幕宽度
	 */
	public int getScreenWidth() {
		return mScreenWidth;
	}

	/**
	 * 
	 * @return 获取屏幕高度
	 */
	public int getScreenHeight() {
		return mScreenHeight;
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
		MobclickAgent.onPause(this);
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
		MobclickAgent.onResume(this);
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
	 * 添加Fragment
	 * 
	 * @param newFragment
	 */
	protected void addFragment(Class<? extends Fragment>... newFragment) {
		addFragment(0, newFragment);
	}

	/**
	 * 添加Fragment
	 * 
	 * @param containerId 容器id
	 * @param newFragment
	 */
	protected void addFragment(int containerId,
			Class<? extends Fragment>... newFragment) {
		setContainer(containerId);
		for (int i = 0; i < newFragment.length; i++) {
			fragments.add(newFragment[i]);
		}
	}

	/**
	 * 设置fragment显示容器的id
	 * 
	 * @param containerId
	 */
	protected void setContainer(int containerId) {
		this.containerId = containerId;
	}


	/**
	 * 获得当前界面显示的Fragment实例
	 */
	protected Fragment getCurrentFragment() {
		return mCurrentFragment;
	}

	/**
	 * 设置当前Activity包含有多少个Fragment
	 * 
	 * @return
	 */
	protected int getFragmentSize() {
		return fragments.size();
	}

	/**
	 * 获得当前选择的Fragment下标
	 * 
	 * @return
	 */
	protected int getCurrentPosition() {
		return currentPosition;
	}



}
