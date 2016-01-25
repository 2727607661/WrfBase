package com.wrf.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wrf.utils.LogUtils;

/**
 * 懒加载的 fragment
 * 
 * @author wrf
 * 
 *        
 * 
 */
public abstract class BaseLazyFragment extends BaseFragment implements
		BaseInitInterface {
	public boolean isFirst = true;
	protected boolean isVisible;
	// 标志位，标志已经初始化完成。
	protected boolean isPrepared;
	public FragmentManager fragManager;
	public View mView;
	private int contentViewId;

	public void init() {
		if (isFirst && isVisible && isPrepared) {
			LogUtils.log("界面首次初始化....");
			initView();
			initData();
			setListener();
			isFirst = false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragManager = getChildFragmentManager();
		contentViewId = setContentViewId();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = View.inflate(context, contentViewId, null);
		isPrepared = true;
		init();
		return mView;
	}

	/**
	 * 在这里实现Fragment数据的缓加载 setUserVisibleHint 在onCreateView之前 ，
	 * 
	 * @param isVisibleToUser
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			init();
		} else {
			isVisible = false;
		}
	}
}
