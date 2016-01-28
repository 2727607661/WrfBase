package com.wrf.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * Created by wrf on 2016/1/28.
 * 有子Fragment 的父类
 */
public abstract class BaseTableFragmentActivity extends BaseFragmentActivity  implements  CreateFragInterface {
    private String lastFragmentTag;
    private View[] views; // 这些view需要添加tag 标记 ；
    private FragmentManager fragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragManager = getSupportFragmentManager();
    }

    public void addFragment(View...  views) {
        this.views = views;
        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(tipsOnClickListener);
        }
        views[0].setSelected(true);
        changeFragment(views[0].getTag().toString());
    }

    private View.OnClickListener tipsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < views.length; i++) {
                views[i].setSelected(false);
            }
            v.setSelected(true);
            changeFragment(v.getTag().toString());
        }
    };

    private void changeFragment(String flag) {
        if (flag != lastFragmentTag) {
            Fragment lastFragment = null;

            if (lastFragmentTag != null) {
                lastFragment = fragManager.findFragmentByTag(lastFragmentTag);
            }
            FragmentTransaction ft = fragManager.beginTransaction();

            if (lastFragment != null) {
                ft.hide(lastFragment);
            }

            Fragment currentFragment = fragManager.findFragmentByTag(flag);

            if (currentFragment != null) {
                ft.show(currentFragment);
            } else {
                ft.add(setframeContentId() , createFragment(flag), flag);
            }
            lastFragmentTag = flag;
            ft.commit();
        }
    }
}
