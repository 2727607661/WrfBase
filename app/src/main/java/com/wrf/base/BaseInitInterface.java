package com.wrf.base;

import android.view.View.OnClickListener;

/**
 * 界面初始化 
 * @author wrf
 * 
 */
public interface BaseInitInterface extends OnClickListener{
	int setContentViewId();
	void initView();
	void initData();
	void setListener();
}
