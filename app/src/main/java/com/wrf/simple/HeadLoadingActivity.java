package com.wrf.simple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wrf.base.BaseActivity;
import com.wrf.widget.HeadLoading;

import base.wrf.com.wrfbase.R;

/**
 * 头部,加载中提示
 */
public class HeadLoadingActivity extends BaseActivity {
    private HeadLoading headLoading;
    private Button btn_start,btn_end ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_loding);
        headLoading = new HeadLoading(this);
        btn_start= $(R.id.btn_start);
        btn_end= $(R.id.btn_end);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headLoading.lodingAnimeStart();
            }
        });
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headLoading.lodingAnimeEnd();
            }
        });
    }
}
