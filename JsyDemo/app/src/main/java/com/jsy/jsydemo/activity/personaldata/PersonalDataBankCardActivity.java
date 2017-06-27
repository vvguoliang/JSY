package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 我的银行卡
 */

public class PersonalDataBankCardActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_bank_card);
        findViewById();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.personal_bank_savings_card:
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
        TextView personal_bank_savings_card = (TextView)findViewById(R.id.personal_bank_savings_card);
        personal_bank_savings_card.setOnClickListener(this);

        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_bank_card));

        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }
}
