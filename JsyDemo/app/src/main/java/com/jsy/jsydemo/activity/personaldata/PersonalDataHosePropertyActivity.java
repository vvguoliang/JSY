package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 房产
 */

public class PersonalDataHosePropertyActivity extends BaseActivity implements View.OnClickListener {


    private TextView house_estate;
    private TextView house_location;
    private TextView house_type;
    private TextView house_market_price;
    private TextView house_mortgage;
    private TextView house_no_mortgage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_hose_peoerty);
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
            case R.id.house_estate:
                break;
            case R.id.house_location:
                break;
            case R.id.house_type:
                break;
            case R.id.house_market_price:
                break;
            case R.id.house_mortgage:
                break;
            case R.id.house_no_mortgage:
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
        title_view.setText(this.getString(R.string.name_loan_personal_data_house_property));

        house_estate = (TextView) findViewById(R.id.house_estate);
        house_location = (TextView) findViewById(R.id.house_location);
        house_type = (TextView) findViewById(R.id.house_type);
        house_market_price = (TextView) findViewById(R.id.house_market_price);
        house_mortgage = (TextView) findViewById(R.id.house_mortgage);
        house_no_mortgage = (TextView) findViewById(R.id.house_no_mortgage);

        house_estate.setOnClickListener(this);
        house_location.setOnClickListener(this);
        house_type.setOnClickListener(this);
        house_market_price.setOnClickListener(this);
        house_mortgage.setOnClickListener(this);
        house_no_mortgage.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }
}
