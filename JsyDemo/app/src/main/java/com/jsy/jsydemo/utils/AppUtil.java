package com.jsy.jsydemo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;

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

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;//拍照权限
    public static final int MY_PERMISSIONS_REQUEST_READ_SD = 101;//读SD卡权限
    public static final int MY_PERMISSIONS_REQUEST_WRITE_SK = 102;//写SD卡权限
    public static final int MY_PERMISSIONS_REQUEST_READ_SD_PHOTOALBUM = 103;//写SD卡权限
    public static final int MY_PERMISSIONS_REQUEST_WRITE_SK_PHOTOALBUM = 104;//写SD卡权限
    public static final int CAPTURE_IMAGE_REQUEST = 104;//拍照后的返回值
    public static final int LOAD_IMAGE_REQUEST = 105;//相册的返回值
    public static final int CLIP_IMAGE_REQUEST = 106;//剪裁图片的返回值
    public static final String IMAGE_TYPE = "image/*";
    public static File mOutFile;//图片uri路径
    public static File mImageFile = null;//图片file路径


    public static Integer mBuildVersion = android.os.Build.VERSION.SDK_INT;//当前SDK版本

}
