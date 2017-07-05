package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/30.
 * 所有证件上传
 */

public class PersonalDataUploadActivity extends BaseActivity implements View.OnClickListener {

    private ImageView upload_front_id;

    private ImageView upload_front_hold_id;

    private ImageView upload_hous;

    private ImageView upload_vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_upload);
        findViewById();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                finish();
                break;
            case R.id.upload_front_id:
                break;
            case R.id.upload_front_hold_id:
                break;
            case R.id.upload_hous:
                break;
            case R.id.upload_vehicle:
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
        title_view.setText(this.getString(R.string.name_loan_personal_data_certificates));

        upload_front_id = (ImageView) findViewById(R.id.upload_front_id);
        upload_front_hold_id = (ImageView) findViewById(R.id.upload_front_hold_id);
        upload_hous = (ImageView) findViewById(R.id.upload_hous);
        upload_vehicle = (ImageView) findViewById(R.id.upload_vehicle);

        upload_front_id.setOnClickListener(this);
        upload_front_hold_id.setOnClickListener(this);
        upload_hous.setOnClickListener(this);
        upload_vehicle.setOnClickListener(this);
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
