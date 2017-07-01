package com.jsy.jsydemo.base;

import android.app.Application;

import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;

/**
 * Created by vvguoliang on 2017/6/23.
 */

public class JSYApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);

        //初始化链接地址
        HttpURL.getInstance().initUrl(this);
    }
}
