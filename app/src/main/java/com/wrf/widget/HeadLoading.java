package com.wrf.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import base.wrf.com.wrfbase.R;

/**
 * Created by wrf on 2016/2/1.
 */
public class HeadLoading {

    private LinearLayout ll_loading;
    private ImageView iv_loading;
    private TextView tv_loading;
    private Context context;

    public HeadLoading(View mView, Context context) {
        this.context = context;
        ll_loading = (LinearLayout) mView.findViewById(R.id.head_loading);
        tv_loading = (TextView) mView.findViewById(R.id.tv_loading);
        iv_loading = (ImageView) mView.findViewById(R.id.iv_loading);
    }

    public HeadLoading(Activity activity) {
        this.context = activity;
        ll_loading = (LinearLayout) activity.findViewById(R.id.head_loading);
        tv_loading = (TextView) activity.findViewById(R.id.tv_loading);
        iv_loading = (ImageView) activity.findViewById(R.id.iv_loading);
    }

    // 加载中动画开始
    public void lodingAnimeStart() {
        ll_loading.setVisibility(View.VISIBLE);
        iv_loading.setVisibility(View.VISIBLE);
        tv_loading.setVisibility(View.VISIBLE);
        Animation operatingAnim = AnimationUtils.loadAnimation(context,
                R.anim.loading_dialog_round);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        iv_loading.startAnimation(operatingAnim);
    }

    public void lodingAnimeEnd() {
        iv_loading.clearAnimation();
        iv_loading.setVisibility(View.GONE);
        ll_loading.setVisibility(View.GONE);
        tv_loading.setVisibility(View.GONE);
    }
}
