package com.jsy.jsydemo.activity.personaldata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/27.
 * 家庭情况
 */

public class PersonalDataFamilyActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

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
                getHttpCredit();
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

        getHttp();

        personal_family_marriage.setOnClickListener(this);
        personal_family_city.setOnClickListener(this);
        personal_family_address.setOnClickListener(this);
        personal_family_household_register.setOnClickListener(this);
    }


    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().FAMILYLIST, "family_list", map, this);
    }

    private void getHttpCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("marriage_status", personal_family_marriage.getText().toString());
        map.put("city", personal_family_city.getText().toString());
        map.put("address", personal_family_address.getText().toString());
        map.put("hj_address", personal_family_household_register.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().FAMILYADD, "family_add", map, this);
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

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "family_list":
                break;
            case "family_add":
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "family_list":
                List<Map<String, String>> maps = JsonData.getInstance().getJsonPersonalDataFamily(result);
                personal_family_marriage.setText(maps.get(0).get("marriage_status"));
                personal_family_city.setText(maps.get(0).get("city"));
                personal_family_address.setText(maps.get(0).get("address"));
                personal_family_household_register.setText(maps.get(0).get("hj_address"));
                break;
            case "family_add":
                finish();
                break;
        }

    }
}
