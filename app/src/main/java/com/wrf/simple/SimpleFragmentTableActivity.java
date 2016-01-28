package com.wrf.simple;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.wrf.base.BaseTableFragmentActivity;

import base.wrf.com.wrfbase.R;


/**
 * Created by wrf on 2016/1/28.
 * fragment table布局
 */
public class SimpleFragmentTableActivity extends BaseTableFragmentActivity {
    private Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_fragment_table);

        btn1 = $( R.id.btn_1);
        btn2 = $(R.id.btn_2);
        btn3 = $( R.id.btn_3);
        btn4 = $( R.id.btn_4);

        btn1.setTag(btn1.getText().toString());
        btn2.setTag(btn2.getText().toString());
        btn3.setTag(btn3.getText().toString());
        btn4.setTag(btn4.getText().toString());

        addFragment(btn1, btn2, btn3, btn4);
    }


    @Override
    public Fragment createFragment(String which) {
        Fragment fragment = null;
        if (which.equals(btn1.getText().toString())) {
            fragment = new TestFragment();
        }else  if (which.equals(btn2.getText().toString())) {
            fragment = new TestFragment();
        }else  if (which.equals(btn3.getText().toString())) {
            fragment = new TestFragment();
        }else  if (which.equals(btn4.getText().toString())) {
            fragment = new TestFragment();
        }
        fragment.setUserVisibleHint(true);
        return fragment;
    }

    @Override
    public int setframeContentId() {
        return R.id.frameContent;
    }
}
