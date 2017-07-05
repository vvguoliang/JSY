package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 运营商验证
 */

public class PersonalDataOperatorActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_operator);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.operator_no_authorization:
                break;
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
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
        title_view.setText(this.getString(R.string.name_loan_personal_data_operator));

        TextView operator_no_authorization = (TextView) findViewById(R.id.operator_no_authorization);
        operator_no_authorization.setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }

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
