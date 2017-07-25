package com.jsy.jsydemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/23.
 * <p>
 * 启动页面
 */

public class CommissioningActivity extends BaseActivity implements View.OnClickListener {

    private ImageView commissioning_image;

    private ImageView commissioning_image1;

    private Button commissioning_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_commissioning);
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            stateBarTint("#305591", true);
            statusFragmentBarDarkMode();
        }
        findViewById();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void findViewById() {
        commissioning_image = (ImageView) findViewById(R.id.commissioning_image);
        commissioning_image1 = (ImageView) findViewById(R.id.commissioning_image1);
        commissioning_button = (Button) findViewById(R.id.commissioning_button);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commissioning_button:
                break;
        }
    }
}
