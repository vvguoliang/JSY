package com.jsy.jsydemo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by vvguoliang on 2017/6/24.
 * <p>
 * APP 操作
 */

public class AppUtil {

    /**
     * 屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] Dispay(Activity context) {
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        return new int[]{screenWidth, screenHeight};
    }

}
