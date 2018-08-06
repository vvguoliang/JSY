package com.jsy.jsydemo.base;

import android.app.Application;
import android.widget.Toast;

import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/23.
 *
 * Application 开始打开启动页面
 */

public class JSYApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler( this );
        Thread.setDefaultUncaughtExceptionHandler( catchExcep );

        //初始化链接地址
        HttpURL.getInstance().initUrl( this );
//使用集成测试服务
        MobclickAgent.setDebugMode( true );


//美洽
        // 替换成自己的key
        String meiqiaKey = "b3d50baced8039719ce30b1e2b0d1187";
        MQConfig.init(this, meiqiaKey, new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
            }

            @Override
            public void onFailure(int code, String message) {
//                Toast.makeText(JSYApplication.this, "int failure message = " + message, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
