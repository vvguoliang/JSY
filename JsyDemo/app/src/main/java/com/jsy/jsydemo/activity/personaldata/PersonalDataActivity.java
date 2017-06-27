package com.jsy.jsydemo.activity.personaldata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 个人资料
 */

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener {

    private TextView personal_data_phone;//手机号

    private TextView personal_data_name;//姓名

    private TextView personal_data_id;//身份证

    private TextView personal_data_complete0, personal_data_complete1, personal_data_complete2, personal_data_complete3,
            personal_data_complete4, personal_data_complete5, personal_data_complete6, personal_data_complete7,
            personal_data_complete8, personal_data_complete9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data);
        findViewById();
    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data));
        findViewById(R.id.personal_data_credit).setOnClickListener(this);
        findViewById(R.id.personal_data_enterprise).setOnClickListener(this);
        findViewById(R.id.personal_data_family).setOnClickListener(this);
        findViewById(R.id.personal_data_other).setOnClickListener(this);
        findViewById(R.id.personal_data_hose_property).setOnClickListener(this);
        findViewById(R.id.personal_data_car_production).setOnClickListener(this);
        findViewById(R.id.personal_data_operator).setOnClickListener(this);
        findViewById(R.id.personal_data_online_shopping).setOnClickListener(this);
        findViewById(R.id.personal_data_certificates).setOnClickListener(this);
        findViewById(R.id.personal_data_bank_card).setOnClickListener(this);
        personal_data_phone = (TextView) findViewById(R.id.personal_data_phone);
        personal_data_name = (TextView) findViewById(R.id.personal_data_name);
        personal_data_id = (TextView) findViewById(R.id.personal_data_id);
        personal_data_complete0 = (TextView) findViewById(R.id.personal_data_complete0);
        personal_data_complete1 = (TextView) findViewById(R.id.personal_data_complete1);
        personal_data_complete2 = (TextView) findViewById(R.id.personal_data_complete2);
        personal_data_complete3 = (TextView) findViewById(R.id.personal_data_complete3);
        personal_data_complete4 = (TextView) findViewById(R.id.personal_data_complete4);
        personal_data_complete5 = (TextView) findViewById(R.id.personal_data_complete5);
        personal_data_complete6 = (TextView) findViewById(R.id.personal_data_complete6);
        personal_data_complete7 = (TextView) findViewById(R.id.personal_data_complete7);
        personal_data_complete8 = (TextView) findViewById(R.id.personal_data_complete8);
        personal_data_complete9 = (TextView) findViewById(R.id.personal_data_complete9);

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image://返回
                finish();
                break;
            case R.id.title_complete://完成
                break;
            case R.id.personal_data_credit://个人资信
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataCreditActivity.class));
                break;
            case R.id.personal_data_enterprise://企业经营情况
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataEnterpriseActivity.class));
                break;
            case R.id.personal_data_family://家庭情况
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataFamilyActivity.class));
                break;
            case R.id.personal_data_other://其他情况
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataOtherActivity.class));
                break;
            case R.id.personal_data_hose_property://房产
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataHosePropertyActivity.class));
                break;
            case R.id.personal_data_car_production://车产
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataCarActivity.class));
                break;
            case R.id.personal_data_operator://运营商验证
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataOperatorActivity.class));
                break;
            case R.id.personal_data_online_shopping://网购信用
                break;
            case R.id.personal_data_certificates://证件上传
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataCertificatesActivity.class));
                break;
            case R.id.personal_data_bank_card://我的银行卡
                startActivity(new Intent(PersonalDataActivity.this, PersonalDataBankCardActivity.class));
                break;
        }

    }
}
