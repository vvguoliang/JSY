package com.jsy.jsydemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/28.
 * 登录页面
 */

public class LogoActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_logo);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_feedback));

    }

    @Override
    protected void initView() {

    }
}
