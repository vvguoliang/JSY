package com.jsy.jsydemo.utils.CameraUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.jsy.jsydemo.interfaces.UserCenterModel;
import com.jsy.jsydemo.utils.AppUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vvguoliang on 2017/7/1.
 *
 * 授权+拍照和剪切
 */

public class UserCenterRealize implements UserCenterModel {

    @Override
    public void getFileByPhotograph(Context context) {
        Activity activity = (Activity) context;
        if (AppUtil.mBuildVersion >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //申请相机权限
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, AppUtil.MY_PERMISSIONS_REQUEST_CAMERA);
            } else {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    //申请读SD权限
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            AppUtil.MY_PERMISSIONS_REQUEST_READ_SD);
                } else {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        //申请写SD权限
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                AppUtil.MY_PERMISSIONS_REQUEST_WRITE_SK);
                    } else {
                        startPhotograph(activity);
                    }
                }
            }
        } else {
            //拍照
            startPhotograph(activity);
        }

    }

    @Override
    public File getImagefile() {
        File mediaStorageDir = null;
        File mediaFile = null;
        try {
            mediaStorageDir = new File(Environment.getExternalStorageDirectory().toString() + "/IMAGE/");
            Log.d("创建文件夹成功", mediaStorageDir.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("创建文件夹失败", mediaStorageDir.toString());
        }
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("创建文件失败", "WRITE_EXTERNAL_STORAGE 权限");
                return null;
            }
        }
        // Create a media file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        Log.d("创建文件成功", mediaFile.toString());
        return mediaFile;

    }

    @Override
    public void getFileByPhotoAlbum(Context context) {
        Activity activity = (Activity) context;
        if (AppUtil.mBuildVersion >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                //申请读SD权限
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        AppUtil.MY_PERMISSIONS_REQUEST_READ_SD_PHOTOALBUM);
            } else {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    //申请写SD权限
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            AppUtil.MY_PERMISSIONS_REQUEST_WRITE_SK_PHOTOALBUM);
                } else {
                    startPhotoAlbum(context);
                }
            }
        } else {
            startPhotoAlbum(context);
        }
    }

    @Override
    public void startPhotoAlbum(Context context) {
        if (!BitmapUtils.existSDCard()) {
            Toast.makeText(context, "未检测到SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        Activity a = (Activity) context;
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(AppUtil.IMAGE_TYPE);
        a.startActivityForResult(getAlbum, AppUtil.LOAD_IMAGE_REQUEST);
    }

    @Override
    public void startPhotograph(Context context) {
        if (!BitmapUtils.existSDCard()) {
            Toast.makeText(context, "未检测到SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        Activity activity = (Activity) context;
        if (AppUtil.mBuildVersion < 24) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            AppUtil.mImageFile = getImagefile();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(AppUtil.mImageFile));
            activity.startActivityForResult(intent, AppUtil.CAPTURE_IMAGE_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues contentValues = new ContentValues(1);
            AppUtil.mImageFile = getImagefile();
            contentValues.put(MediaStore.Images.Media.DATA, AppUtil.mImageFile.getAbsolutePath());
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, AppUtil.CAPTURE_IMAGE_REQUEST);
        }
    }


    @Override
    public void startClip(Activity activity, File file) {
        if (null == file) {
            return;
        }
        AppUtil.mOutFile = getImagefile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(activity, file), AppUtil.IMAGE_TYPE);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(AppUtil.mOutFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, AppUtil.CLIP_IMAGE_REQUEST);
    }

    @Override
    public Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Uri uri = null;
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            uri = Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                uri = context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                uri = null;
            }
        }
        return uri;
    }

}
