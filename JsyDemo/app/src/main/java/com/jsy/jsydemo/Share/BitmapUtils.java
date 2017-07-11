package com.jsy.jsydemo.Share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BitmapUtils {

    /**
     * 文件保存的路径
     */
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache/pics";

    /**
     * 从本地SD卡获取网络图片，key是url的MD5值
     * @return
     */
    public static void getBitmap(Bitmap bitmap) {
        String fileName = "sxs_icon_share.png";

        File file = new File(FILE_PATH, fileName);
        FileOutputStream out = null;
        try {
            if (!file.getParentFile().exists()) {
                file.mkdirs();
            }
            file.createNewFile();
            if (file.exists()) {
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从本地SD卡获取网络图片，key是url的MD5值
     *
     * @return
     */
    public static void saveBitmap(Bitmap bitmap) {
        String fileName = "sxs_icon_share.png";
        File file = new File(FILE_PATH + "/sxs/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //网络图片转成Bitmap
    public static Bitmap getUrlBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

}
