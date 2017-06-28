package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 车产
 */

public class PersonalDataCarActivity extends BaseActivity implements View.OnClickListener {

    private TextView car_estate;
    private TextView car_new_car;
    private TextView car_life;
    private TextView car_mortgage;
    private TextView car_no_mortgage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_car);
        findViewById();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_estate:
                break;
            case R.id.car_new_car:
                break;
            case R.id.car_life:
                break;
            case R.id.car_mortgage:
                break;
            case R.id.car_no_mortgage:
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
        title_view.setText(this.getString(R.string.name_loan_personal_data_car_production));

        car_estate = (TextView) findViewById(R.id.car_estate);
        car_new_car = (TextView) findViewById(R.id.car_new_car);
        car_life = (TextView) findViewById(R.id.car_life);
        car_mortgage = (TextView) findViewById(R.id.car_mortgage);
        car_no_mortgage = (TextView) findViewById(R.id.car_no_mortgage);

        car_estate.setOnClickListener(this);
        car_new_car.setOnClickListener(this);
        car_life.setOnClickListener(this);
        car_mortgage.setOnClickListener(this);
        car_no_mortgage.setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }
}
