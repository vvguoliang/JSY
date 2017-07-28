package com.jsy.jsydemo.activity.personaldata;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LogoActivity;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 个人资料
 */

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView personal_data_phone;//手机号

    private EditText personal_data_name;//姓名

    private EditText personal_data_id;//身份证

    private TextView personal_data_complete0, personal_data_complete1, personal_data_complete2, personal_data_complete3,
            personal_data_complete4, personal_data_complete5, personal_data_complete6, //personal_data_complete7,
            personal_data_complete8, personal_data_complete9;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data);
        getHpptuserInfo();
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.setStateBar(this, Color.parseColor("#305591"));
        }
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
//        findViewById(R.id.personal_data_online_shopping).setOnClickListener(this);
        findViewById(R.id.personal_data_certificates).setOnClickListener(this);
        findViewById(R.id.personal_data_bank_card).setOnClickListener(this);

        personal_data_phone = (TextView) findViewById(R.id.personal_data_phone);
        personal_data_name = (EditText) findViewById(R.id.personal_data_name);
        if (!StringUtil.isNullOrEmpty(SharedPreferencesUtils.get(this, "uid", "").toString())) {
            personal_data_phone.setText(SharedPreferencesUtils.get(this, "username", "").toString());
        }
        if (!StringUtil.isNullOrEmpty(SharedPreferencesUtils.get(this, "realname", "").toString())) {
            personal_data_name.setText(SharedPreferencesUtils.get(this, "realname", "").toString());
        } else {
            personal_data_name.setText("");
        }
        personal_data_id = (EditText) findViewById(R.id.personal_data_id);
        if (!StringUtil.isNullOrEmpty(SharedPreferencesUtils.get(this, "idcard", "").toString())) {
            personal_data_id.setText(SharedPreferencesUtils.get(this, "idcard", "").toString());
        } else {
            personal_data_id.setText("");
        }
        personal_data_complete0 = (TextView) findViewById(R.id.personal_data_complete0);
        personal_data_complete1 = (TextView) findViewById(R.id.personal_data_complete1);
        personal_data_complete2 = (TextView) findViewById(R.id.personal_data_complete2);
        personal_data_complete3 = (TextView) findViewById(R.id.personal_data_complete3);
        personal_data_complete4 = (TextView) findViewById(R.id.personal_data_complete4);
        personal_data_complete5 = (TextView) findViewById(R.id.personal_data_complete5);
        personal_data_complete6 = (TextView) findViewById(R.id.personal_data_complete6);
//        personal_data_complete7 = (TextView) findViewById(R.id.personal_data_complete7);
        personal_data_complete8 = (TextView) findViewById(R.id.personal_data_complete8);
        personal_data_complete9 = (TextView) findViewById(R.id.personal_data_complete9);

    }

    @Override
    protected void initView() {
    }

    private void getHpptuserInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().USERINFO, "username_uid", map, this);
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("realname", personal_data_name.getText().toString());
        map.put("idcard", personal_data_id.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().USERINFOADD, "username_add", map, this);
    }

    /**
     * 个人资料拉去
     */
    private void getHttpstater() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().STATUS, "username_list", map, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image://返回
                finish();
                break;
            case R.id.title_complete://完成
                getHttp();
                break;
            case R.id.personal_data_credit://个人资信
                intent = new Intent(PersonalDataActivity.this, PersonalDataCreditActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.personal_data_enterprise://企业经营情况
                intent = new Intent(PersonalDataActivity.this, PersonalDataEnterpriseActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.personal_data_family://家庭情况
                intent = new Intent(PersonalDataActivity.this, PersonalDataFamilyActivity.class);
                startActivityForResult(intent, 102);
                break;
            case R.id.personal_data_other://其他情况
                intent = new Intent(PersonalDataActivity.this, PersonalDataOtherActivity.class);
                startActivityForResult(intent, 103);
                break;
            case R.id.personal_data_hose_property://房产
                intent = new Intent(PersonalDataActivity.this, PersonalDataHosePropertyActivity.class);
                startActivityForResult(intent, 104);
                break;
            case R.id.personal_data_car_production://车产
                intent = new Intent(PersonalDataActivity.this, PersonalDataCarActivity.class);
                startActivityForResult(intent, 105);
                break;
            case R.id.personal_data_operator://运营商验证
                intent = new Intent(PersonalDataActivity.this, PersonalDataOperatorActivity.class);
                intent.putExtra("operator", personal_data_complete6.getText().toString());
                startActivityForResult(intent, 106);
                break;
//            case R.id.personal_data_online_shopping://网购信用
//                break;
            case R.id.personal_data_certificates://证件上传
                intent = new Intent(PersonalDataActivity.this, PersonalDataUploadActivity.class);
                startActivityForResult(intent, 107);
                break;
            case R.id.personal_data_bank_card://我的银行卡
                intent = new Intent(PersonalDataActivity.this, PersonalDataBankCardActivity.class);
                startActivityForResult(intent, 108);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String complete = "";
        if(!TextUtils.isEmpty(data.getExtras().getString("complete"))){
            complete = data.getExtras().getString("complete");
        }
        switch (requestCode) {
            case 100:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete0.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete0.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
            case 101:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete1.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete1.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
            case 102:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete2.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete3.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
            case 103:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete3.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete3.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
            case 104:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete4.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete4.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
            case 105:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete5.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete5.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
            case 106:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete6.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete6.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
            case 107:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete8.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete8.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
            case 108:
                if(!TextUtils.isEmpty(complete) && complete.equals("1")){
                    personal_data_complete9.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_complete));
                }else{
                    personal_data_complete9.setText(PersonalDataActivity.this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHttpstater();
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
            case "username_uid":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
            case "username_add":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
            case "username_list":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        JSONObject object;
        switch (name) {
            case "username_uid":
                object = new JSONObject(result);
                if (object.optString("code").equals("0000")) {
                    object = new JSONObject(object.optString("data"));
                    object = new JSONObject(object.optString("data"));
                    SharedPreferencesUtils.put(this, "uid", object.optString("uid"));
                    personal_data_name.setText(object.optString("realname"));
                    personal_data_id.setText(object.optString("idcard"));
                }
                break;
            case "username_add":
                object = new JSONObject(result);
                if (object.optString("code").equals("0000")) {
                    SharedPreferencesUtils.put(this, "realname", personal_data_name.getText().toString());
                    SharedPreferencesUtils.put(this, "idcard", personal_data_id.getText().toString());
                    finish();
                }
                break;
            case "username_list":
                object = new JSONObject(result);
                object = new JSONObject(object.optString("data"));
                if (object.optString("credit_status").equals("1")) {//人资信完成状态
                    personal_data_complete0.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete0.setText(this.getString(R.string.name_loan_personal_data_no_complete));
                }
                if (object.optString("company_status").equals("1")) {//企业情况完成状态
                    personal_data_complete1.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete1.setText(this.getString(R.string.name_loan_personal_data_no_complete));
                }
                if (object.optString("other_status").equals("1")) {//其他联系人完成状态
                    personal_data_complete3.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete3.setText(this.getString(R.string.name_loan_personal_data_no_complete));
                }
                if (object.optString("house_status").equals("1")) {//房产完成状态
                    personal_data_complete4.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete4.setText(this.getString(R.string.name_loan_personal_data_no_complete));
                }
                if (object.optString("car_status").equals("1")) {//车产完成状态
                    personal_data_complete5.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete5.setText(this.getString(R.string.name_loan_personal_data_no_complete));
                }
                if (object.optString("operator_status").equals("1")) {//运营商完成状态
                    personal_data_complete6.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete6.setText(this.getString(R.string.name_loan_personal_data_no_complete));

                }
                if (object.optString("shopping_status").equals("1")) {//网购信用完成状态

                } else {

                }
                if (object.optString("papers_status").equals("1")) {//证件上传完成状态
                    personal_data_complete8.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete8.setText(this.getString(R.string.name_loan_personal_data_no_complete));
                }

                if (object.optString("bankcard_status").equals("1")) {//我的银行卡完成状态
                    personal_data_complete9.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete9.setText(this.getString(R.string.name_loan_personal_data_no_complete));
                }
                if (object.optString("family_status").equals("1")) {//家庭情况完成状态
                    personal_data_complete2.setText(this.getString(R.string.name_loan_personal_data_complete));
                } else {
                    personal_data_complete2.setText(this.getString(R.string.name_loan_personal_data_no_complete));
                }
                break;
        }
    }
}
