package com.wrf.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import uk.co.senab.photoview.sample.ImagePagerActivity;

/**
 * Created by wrf on 2016/1/22.
 */
public class UiUtils {
    /**
     * 显示提示信息
     */
    public static void toast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    /**
     * 跳转至图片浏览器
     *
     * @param context
     * @param imageUrls 浏览图片的URL字符串数组
     * @param position  当前浏览的图片下标
     */
    public static void gotoImagesBrowse(Context context, String[] imageUrls,
                                        int position) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra("images", imageUrls);
        intent.putExtra("position", position);
        context.startActivity(intent);

    }

    public static void gotoImagesBrowse(Context context, List<String> imageUrls,
                                        int position) {
        String[] imgs = new String[imageUrls.size()];
        for (int i = 0; i < imageUrls.size(); i++) {
            imgs[i] = imageUrls.get(i);
        }
        gotoImagesBrowse(context,imgs,position);
    }
}
