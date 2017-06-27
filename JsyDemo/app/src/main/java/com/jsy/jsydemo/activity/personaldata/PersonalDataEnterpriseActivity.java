package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 企业经营情况
 */

public class PersonalDataEnterpriseActivity extends BaseActivity implements View.OnClickListener {

    private TextView personal_enterprise_identity;
    private TextView personal_enterprise_shares;
    private TextView personal_enterprise_location;
    private TextView personal_enterprise_type;
    private TextView personal_enterprise_time;
    private TextView personal_enterprise_industry;
    private TextView personal_enterprise_life;
    private TextView personal_enterprise_private_water;
    private TextView personal_enterprise_public_water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_enterprise);
        findViewById();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.personal_enterprise_identity:
                break;
            case R.id.personal_enterprise_shares:
                break;
            case R.id.personal_enterprise_location:
                break;
            case R.id.personal_enterprise_type:
                break;
            case R.id.personal_enterprise_time:
                break;
            case R.id.personal_enterprise_industry:
                break;
            case R.id.personal_enterprise_life:
                break;
            case R.id.personal_enterprise_private_water:
                break;
            case R.id.personal_enterprise_public_water:
                break;
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                finish();
                break;
        }

    }

    @Override
    protected void findViewById() {
        personal_enterprise_identity = (TextView) findViewById(R.id.personal_enterprise_identity);
        personal_enterprise_shares = (TextView) findViewById(R.id.personal_enterprise_shares);
        personal_enterprise_location = (TextView) findViewById(R.id.personal_enterprise_location);
        personal_enterprise_type = (TextView) findViewById(R.id.personal_enterprise_type);
        personal_enterprise_time = (TextView) findViewById(R.id.personal_enterprise_time);
        personal_enterprise_industry = (TextView) findViewById(R.id.personal_enterprise_industry);
        personal_enterprise_life = (TextView) findViewById(R.id.personal_enterprise_life);
        personal_enterprise_private_water = (TextView) findViewById(R.id.personal_enterprise_private_water);
        personal_enterprise_public_water = (TextView) findViewById(R.id.personal_enterprise_public_water);

        personal_enterprise_identity.setOnClickListener(this);
        personal_enterprise_shares.setOnClickListener(this);
        personal_enterprise_location.setOnClickListener(this);
        personal_enterprise_type.setOnClickListener(this);
        personal_enterprise_time.setOnClickListener(this);
        personal_enterprise_industry.setOnClickListener(this);
        personal_enterprise_life.setOnClickListener(this);
        personal_enterprise_private_water.setOnClickListener(this);
        personal_enterprise_public_water.setOnClickListener(this);

        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_enterprise));

        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }
}
