package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/27.
 * 家庭情况
 */

public class PersonalDataFamilyActivity extends BaseActivity implements View.OnClickListener {

    private TextView personal_family_marriage;
    private TextView personal_family_city;
    private TextView personal_family_address;
    private TextView personal_family_household_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_family);
        findViewById();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.personal_family_marriage:
                break;
            case R.id.personal_family_city:
                break;
            case R.id.personal_family_address:
                break;
            case R.id.personal_family_household_register:
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

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_family));

        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);

        personal_family_marriage = (TextView) findViewById(R.id.personal_family_marriage);
        personal_family_city = (TextView) findViewById(R.id.personal_family_city);
        personal_family_address = (TextView) findViewById(R.id.personal_family_address);
        personal_family_household_register = (TextView) findViewById(R.id.personal_family_household_register);

        personal_family_marriage.setOnClickListener(this);
        personal_family_city.setOnClickListener(this);
        personal_family_address.setOnClickListener(this);
        personal_family_household_register.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }
}
