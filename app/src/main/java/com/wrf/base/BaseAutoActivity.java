package com.wrf.base;

import android.os.Bundle;
/**
 * 
 * @author chw
 *
 */
public abstract class BaseAutoActivity extends BaseActivity implements BaseInitInterface {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(setContentViewId());
		initView();
		initData();
		setListener();
	}
}
