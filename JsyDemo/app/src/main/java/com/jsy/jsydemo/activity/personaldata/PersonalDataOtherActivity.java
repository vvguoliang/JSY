package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/27.
 * 其他联系人
 */

public class PersonalDataOtherActivity extends BaseActivity implements View.OnClickListener {

    private TextView other_relatives_name;

    private TextView other_relatives_phone;

    private TextView other_contacts_name;

    private TextView other_contacts_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_other);
        findViewById();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                break;
            case R.id.other_relatives_wathet:
                break;
            case R.id.other_contacts_wathet:
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
        title_view.setText(this.getString(R.string.name_loan_personal_data_other));

        findViewById(R.id.other_relatives_wathet).setOnClickListener(this);
        findViewById(R.id.other_contacts_wathet).setOnClickListener(this);

        other_relatives_name = (TextView) findViewById(R.id.other_relatives_name);
        other_relatives_phone = (TextView) findViewById(R.id.other_relatives_phone);
        other_contacts_name = (TextView) findViewById(R.id.other_contacts_name);
        other_contacts_phone = (TextView) findViewById(R.id.other_contacts_phone);

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
