package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.IdcardValidator;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.TimeUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.webview.LoanWebViewActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/7/4.
 * 基本信息认证
 */

public class BasicAuthenticationActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView loan_basic_please_in_text;
    private TextView loan_basic_number_text;
    private TextView loan_basic_application_text;
    private TextView loan_loan_application_text;
    private TextView loan_loan_highest_text;
    private TextView loan_education_level_text;
    private TextView loan_basic_security_text;
    private TextView loan_basic_cat_text;
    private TextView loan_basic_typ_text;

    private EditText loan_basic_please_in_editText;
    private EditText loan_details_basic_id_editText;
    private EditText loan_loan_highest_editText;
    private EditText loan_basic_content_editText;

    private CheckBox loan_basic_image_image;

    private TextView loan_details_basic_loan_text;

    private Button loan_basic_button;

    private EditText loan_basic_application;
    private TextView loan_loan_application;
    private TextView loan_education_level;
    private TextView loan_basic_security;
    private TextView loan_basic_cat;
    private TextView loan_basic_typ;
    private TextView loan_basic_life;

    private boolean ischerBox = false;

    private Intent intent = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_basic_authentication);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B(this);
        }
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loan_loan_application_linear:
            case R.id.loan_loan_application:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"1期", "3期", "6期", "12期", "24期", "36期", "48期"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "application", strings, mHandler, 6);
                }
                break;
            case R.id.loan_education_level_linear:
            case R.id.loan_education_level:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"硕士以上", "本科", "大专", "中专/高中及一下"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "education_level", strings, mHandler, 1);
                }
                break;
            case R.id.loan_basic_security_linear:
            case R.id.loan_basic_security:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"缴纳本地社保", "未缴纳社保"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "basic_security", strings, mHandler, 2);
                }
                break;
            case R.id.loan_basic_cat_linear:
            case R.id.loan_basic_cat:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"无车", "本人名下有车,无贷款", "本人名下有车,有按揭贷款", "本人名下有车,但已被抵押", "其他(请备注)"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "basic_cat", strings, mHandler, 3);
                }
                break;
            case R.id.loan_basic_typ_linear:
            case R.id.loan_basic_typ:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"企业主", "个体工商户", "上班人群", "学生", "无固定职业"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "basic_typ", strings, mHandler, 4);
                }
                break;
            case R.id.loan_basic_life_linear:
            case R.id.loan_basic_life:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    String[] strings = new String[]{"0-5个月", "6-12个月", "1-3年", "3-7年", "7年以上"};
                    //弹出Toast或者Dialog
                    ShowDialog.getInstance().showDialog(this, "basic_life", strings, mHandler,
                            5);
                }
                break;
            case R.id.loan_basic_button:
                if (ischerBox) {
                    if (TextUtils.isEmpty(loan_basic_please_in_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入本人姓名");
                    } else if (loan_basic_please_in_editText.getText().toString().length() < 2) {
                        ToatUtils.showShort1(this, "输入本人姓名不正确");
                        loan_basic_please_in_editText.setText("");
                    } else if (TextUtils.isEmpty(loan_details_basic_id_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入身份证号");
                    } else if (IdcardValidator.getInstance().isValidatedAllIdcard(loan_details_basic_id_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入正确身份证号");
                        loan_details_basic_id_editText.setText("");
                    } else if (TextUtils.isEmpty(loan_basic_application.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入申请金额");
                    } else if (TextUtils.isEmpty(loan_loan_highest_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入最高还款额度");
                    } else if (TextUtils.isEmpty(loan_basic_content_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入月工资");
                    } else {
                        getHttp();
                    }
                } else {
                    ToatUtils.showShort1(this, "您有些数据没有填写完或者没有同意贷款通知书");
                }
                break;
            case R.id.loan_details_basic_loan_text:
                intent = new Intent(BasicAuthenticationActivity.this, LoanWebViewActivity.class);
                intent.putExtra("url", HttpURL.getInstance().HTTP_DETAILS);
                startActivity(intent);
                break;
            case R.id.title_image:
                intent = new Intent();
                intent.putExtra("operator", "2");
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_basic_information));

        loan_basic_please_in_text = findViewById(R.id.loan_basic_please_in_text);
        loan_basic_number_text = findViewById(R.id.loan_basic_number_text);
        loan_basic_application_text = findViewById(R.id.loan_basic_application_text);
        loan_loan_application_text = findViewById(R.id.loan_loan_application_text);
        loan_loan_highest_text = findViewById(R.id.loan_loan_highest_text);
        loan_education_level_text = findViewById(R.id.loan_education_level_text);
        loan_basic_security_text = findViewById(R.id.loan_basic_security_text);
        loan_basic_cat_text = findViewById(R.id.loan_basic_cat_text);
        loan_basic_typ_text = findViewById(R.id.loan_basic_typ_text);

        findViewById(R.id.loan_basic_application_linear).setOnClickListener(this);
        findViewById(R.id.loan_loan_application_linear).setOnClickListener(this);
        findViewById(R.id.loan_education_level_linear).setOnClickListener(this);
        findViewById(R.id.loan_basic_security_linear).setOnClickListener(this);
        findViewById(R.id.loan_basic_cat_linear).setOnClickListener(this);
        findViewById(R.id.loan_basic_typ_linear).setOnClickListener(this);
        findViewById(R.id.loan_basic_life_linear).setOnClickListener(this);

        loan_basic_please_in_editText = findViewById(R.id.loan_basic_please_in_editText);
        loan_details_basic_id_editText = findViewById(R.id.loan_details_basic_id_editText);
        loan_loan_highest_editText = findViewById(R.id.loan_loan_highest_editText);
        loan_basic_content_editText = findViewById(R.id.loan_basic_content_editText);

        loan_basic_application = findViewById(R.id.loan_basic_application);
        loan_loan_application = findViewById(R.id.loan_loan_application);
        loan_education_level = findViewById(R.id.loan_education_level);
        loan_basic_security = findViewById(R.id.loan_basic_security);
        loan_basic_typ = findViewById(R.id.loan_basic_typ);
        loan_basic_cat = findViewById(R.id.loan_basic_cat);
        loan_basic_life = findViewById(R.id.loan_basic_life);

        loan_details_basic_loan_text = findViewById(R.id.loan_details_basic_loan_text);
        loan_basic_image_image = findViewById(R.id.loan_basic_image_image);
        loan_basic_button = findViewById(R.id.loan_basic_button);


    }

    @Override
    protected void initView() {
        loan_basic_button.setOnClickListener(this);
        loan_loan_application.setOnClickListener(this);
        loan_education_level.setOnClickListener(this);
        loan_basic_security.setOnClickListener(this);
        loan_basic_typ.setOnClickListener(this);
        loan_basic_cat.setOnClickListener(this);
        loan_basic_life.setOnClickListener(this);
        loan_details_basic_loan_text.setOnClickListener(this);

        if (!TextUtils.isEmpty(SharedPreferencesUtils.get(this, "realname", "").toString())) {
            loan_basic_please_in_editText.setText(SharedPreferencesUtils.get(this, "realname", "").toString());
        }

        if (!TextUtils.isEmpty(SharedPreferencesUtils.get(this, "idcard", "").toString())) {
            loan_details_basic_id_editText.setText(SharedPreferencesUtils.get(this, "idcard", "").toString());
        }

        loan_basic_image_image.setChecked(true);
        ischerBox = true;
        loan_basic_image_image.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ischerBox = true;
                    loan_basic_button.setBackgroundResource(R.color.common_basic_red);
                } else {
                    ischerBox = false;
                    loan_basic_button.setBackgroundResource(R.color.common_loan_personal);
                }
            }
        });
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("name", loan_basic_please_in_editText.getText().toString());
        map.put("idcard", loan_details_basic_id_editText.getText().toString());
        map.put("money", loan_basic_application.getText().toString());
        map.put("deadline", loan_loan_application.getText().toString());
        map.put("limit", loan_loan_highest_editText.getText().toString());
        map.put("edu", loan_education_level.getText().toString());
        map.put("insurance", loan_basic_security.getText().toString());
        map.put("car_status", loan_basic_cat.getText().toString());
        map.put("profession", loan_basic_typ.getText().toString());
        map.put("salary", loan_basic_content_editText.getText().toString());
        map.put("work_year", loan_basic_life.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().BASEADD, "basic", map, this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (TextUtils.isEmpty(msg.obj.toString())) {
                        ischerBox = false;
                    } else {
                        loan_education_level.setText(msg.obj.toString());
                    }
                    break;
                case 2:
                    if (TextUtils.isEmpty(msg.obj.toString())) {
                        ischerBox = false;
                    } else {
                        loan_basic_security.setText(msg.obj.toString());
                    }
                    break;
                case 3:
                    if (TextUtils.isEmpty(msg.obj.toString())) {
                        ischerBox = false;
                    } else {
                        loan_basic_cat.setText(msg.obj.toString());
                    }
                    break;
                case 4:
                    if (TextUtils.isEmpty(msg.obj.toString())) {
                        ischerBox = false;
                    } else {
                        loan_basic_typ.setText(msg.obj.toString());
                    }
                    break;
                case 5:
                    if (TextUtils.isEmpty(msg.obj.toString())) {
                        ischerBox = false;
                    } else {
                        loan_basic_life.setText(msg.obj.toString());
                    }
                    break;
                case 6:
                    if (TextUtils.isEmpty(msg.obj.toString())) {
                        ischerBox = false;
                    } else {
                        loan_loan_application.setText(msg.obj.toString());
                    }
                    break;
            }
        }
    };

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
            case "basic":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "basic":
                intent = new Intent();
                intent.putExtra("operator", "1");
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            intent.putExtra("operator", "2");
            setResult(RESULT_CANCELED, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
