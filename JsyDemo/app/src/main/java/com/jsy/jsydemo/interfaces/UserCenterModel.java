package com.jsy.jsydemo.interfaces;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * Created by vvguoliang on 2017/7/1.
 */

public interface UserCenterModel {
    /**
     * 拍照
     * @param context
     */
    void getFileByPhotograph(Context context);

    /**
     * 相册
     * @param context
     */
    void getFileByPhotoAlbum(Context context);

    /**
     * 创建img文件
     * @return
     */
    File getImagefile();

    /**
     * 开始拍照
     * @param context
     */
    void startPhotograph(Context context);

    /**
     * 调用相册
     * @param context
     */
    void startPhotoAlbum(Context context);


    /**
     * 剪裁图片
     * @param activity
     */
    void startClip(Activity activity, File file);

    /**
     * API24 以上调用
     * @param context
     * @param imageFile
     * @return
     */
    Uri getImageContentUri(Context context, File imageFile);
}