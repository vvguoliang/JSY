package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 证件上传
 */

public class PersonalDataCertificatesActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_complete;

    private RelativeLayout personal_data_certificates_positive;

    private RelativeLayout personal_data_certificates_other_sid;

    private RelativeLayout personal_data_certificates_face_recognition;

    private TextView face_recognition_correct_text;

    private ImageView face_recognition_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_certifcates);
    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        title_complete = (TextView) findViewById(R.id.title_complete);
        title_complete.setVisibility(View.VISIBLE);
        title_complete.setOnClickListener(this);
        title_complete.setText(this.getString(R.string.name_loan_personal_data_preservation));
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data));

        face_recognition_correct_text = (TextView) findViewById(R.id.face_recognition_correct_text);

        face_recognition_camera = (ImageView) findViewById(R.id.face_recognition_camera);

        findViewById(R.id.personal_data_certificates_positive).setOnClickListener(this);
        findViewById(R.id.personal_data_certificates_other_sid).setOnClickListener(this);
        findViewById(R.id.personal_data_certificates_face_recognition).setOnClickListener(this);
    }

    @Override
    protected void initView() {

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
            case R.id.personal_data_certificates_positive:
                break;
            case R.id.personal_data_certificates_other_sid:
                break;
            case R.id.personal_data_certificates_face_recognition:
                break;
        }
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
