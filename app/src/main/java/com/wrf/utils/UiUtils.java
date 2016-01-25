package com.wrf.utils;

import android.content.Context;
import android.widget.Toast;

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
}
