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

@SuppressWarnings("ResultOfMethodCallIgnored")
public class BitmapUtils {

    /**
     * 文件保存的路径
     */
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 从本地SD卡获取网络图片，key是url的MD5值
     *
     * @return
     */
    public static void getBitmap(Bitmap bitmap) {
        try {
            String fileName = "jsy_ic_launcher.png";
            File file = new File(FILE_PATH + "/" + fileName);
            if (!file.exists()) {
                file.mkdir();
            }
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
