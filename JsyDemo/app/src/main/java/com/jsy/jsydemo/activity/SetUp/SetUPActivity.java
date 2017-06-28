package com.jsy.jsydemo.activity.SetUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/28.
 * <p>
 * 设置
 */

public class SetUPActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set_up);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                break;
            case R.id.set_up_about:
                startActivity(new Intent(SetUPActivity.this, SetUpAboutActivity.class));
                break;
            case R.id.set_up_password:
                break;
            case R.id.set_up_sign_out:
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_setup));

        findViewById(R.id.set_up_about).setOnClickListener(this);
        findViewById(R.id.set_up_password).setOnClickListener(this);
        findViewById(R.id.set_up_sign_out).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }
}
