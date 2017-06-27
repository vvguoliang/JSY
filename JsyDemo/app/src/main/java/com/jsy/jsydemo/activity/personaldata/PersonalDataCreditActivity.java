package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/27.
 * 个人资信
 */

public class PersonalDataCreditActivity extends BaseActivity implements View.OnClickListener {

    private TextView personal_credit_degree_education;

    private TextView personal_no_cards;

    private TextView personal_no_cards_record;

    private TextView personal_credit_liabilities;

    private TextView personal_credit_no_loan;

    private TextView personal_credit_no_taobao;

    private TextView personal_credit_purpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_credit);
    }

    @Override
    protected void findViewById() {
        personal_credit_degree_education = (TextView) findViewById(R.id.personal_credit_degree_education);
        personal_no_cards = (TextView) findViewById(R.id.personal_no_cards);
        personal_no_cards_record = (TextView) findViewById(R.id.personal_no_cards_record);
        personal_credit_liabilities = (TextView) findViewById(R.id.personal_credit_liabilities);
        personal_credit_no_loan = (TextView) findViewById(R.id.personal_credit_no_loan);
        personal_credit_no_taobao = (TextView) findViewById(R.id.personal_credit_no_taobao);
        personal_credit_purpose = (TextView) findViewById(R.id.personal_credit_purpose);

        personal_credit_degree_education.setOnClickListener(this);
        personal_no_cards.setOnClickListener(this);
        personal_no_cards_record.setOnClickListener(this);
        personal_credit_liabilities.setOnClickListener(this);
        personal_credit_no_loan.setOnClickListener(this);
        personal_credit_no_taobao.setOnClickListener(this);
        personal_credit_purpose.setOnClickListener(this);

        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_credit));

        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_credit_degree_education://文化程度
                break;
            case R.id.personal_no_cards://有无信用卡
                break;
            case R.id.personal_no_cards_record://两年内应用记录
                break;
            case R.id.personal_credit_liabilities://负债情况
                break;
            case R.id.personal_credit_no_loan://有无成功贷款情况
                break;
            case R.id.personal_credit_no_taobao://是否实名淘宝
                break;
            case R.id.personal_credit_purpose://贷款用途
                break;
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete://完成
                break;
        }

    }
}
