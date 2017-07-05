package com.jsy.jsydemo.activity;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/23.
 * <p>
 * 启动页面
 */

public class CommissioningActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
