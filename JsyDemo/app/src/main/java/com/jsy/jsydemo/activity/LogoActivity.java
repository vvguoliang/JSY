package com.jsy.jsydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.RegisterSignCodeModify;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.SetUp.SetUpPasswordActivity;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.PublicClass.CountDownTimerUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/28.
 * 登录页面
 */

public class LogoActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private LinearLayout tab_activity_lin;

    private ImageView title_image;

    private TextView title_complete;

    private EditText loan_logo_edittext_phone;

    private EditText loan_logo_edittext_code;

    private EditText loan_logo_edittext_password_code;

    private Button loan_logo_button_code;

    private TextView loan_logo_account_number;

    private LinearLayout loan_logo_Re_code;

    private RelativeLayout loan_logo_Re_password;

    private TextView loan_logo_no_password;

    private int logoCodeLogo = R.string.name_loan_logo_code_logo;

    private int logoAccountNumber = R.string.name_loan_logo_account_number;

    private String phone = "";

    private CountDownTimerUtils mCountDownTimerUtils;

    private int logintype = 0;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_logo);
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loan_logo_account_number://账号登入 切换
                if (loan_logo_account_number.getText().toString().equals(LogoActivity.this.getString(logoAccountNumber))) {
                    loan_logo_Re_code.setVisibility(View.GONE);
                    loan_logo_Re_password.setVisibility(View.VISIBLE);
                    loan_logo_account_number.setText(LogoActivity.this.getString(logoCodeLogo));
                    loan_logo_no_password.setVisibility(View.VISIBLE);
                } else {
                    loan_logo_Re_code.setVisibility(View.VISIBLE);
                    loan_logo_Re_password.setVisibility(View.GONE);
                    loan_logo_account_number.setText(LogoActivity.this.getString(logoAccountNumber));
                    loan_logo_no_password.setVisibility(View.GONE);
                }
                break;
            case R.id.loan_logo_button_code://验证码
                if (StringUtil.isNullOrEmpty(phone)) {
                    ToatUtils.showShort1(this, "您输入的手机号码不能为空");
                } else if (!StringUtil.isMobileNO(phone)) {
                    ToatUtils.showShort1(this, "您输入的手机号码不正确,请重新输入");
                    loan_logo_edittext_phone.setText("");
                } else {
                    mCountDownTimerUtils = new CountDownTimerUtils(loan_logo_button_code, 60 * 1000, 1000);
                    mCountDownTimerUtils.start();
                    Map<String, Object> map = new HashMap<>();
                    map.put("mobile", (int) Double.parseDouble(phone));
                    map.put("password", "");
                    map.put("code", 0);
                    OkHttpManager.postAsync(HttpURL.getInstance().CODE, "code", map, this);
                }
                break;
            case R.id.loan_logo_button_logo://登入按钮
                if (StringUtil.isNullOrEmpty(phone)) {
                    ToatUtils.showShort1(this, "您输入的手机号码不能为空");
                    return;
                } else if (!StringUtil.isMobileNO(phone)) {
                    ToatUtils.showShort1(this, "您输入的手机号码不正确,请重新输入");
                    loan_logo_edittext_phone.setText("");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("mobile", (int) Double.parseDouble(phone));
                if (loan_logo_account_number.getText().toString().equals(LogoActivity.this.getString(logoAccountNumber))) {
                    logintype = 1;
                    if (StringUtil.isNullOrEmpty(loan_logo_edittext_password_code.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入密码");
                        return;
                    }
                    map.put("code", 0);
                    map.put("logintype", logintype);
                    map.put("password", loan_logo_edittext_password_code.getText().toString());
                } else {
                    logintype = 2;
                    if (StringUtil.isNullOrEmpty(loan_logo_edittext_code.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入验证码");
                        return;
                    }
                    map.put("code", (int) Double.parseDouble(loan_logo_edittext_code.getText().toString()));
                    map.put("password", "");
                    map.put("logintype", logintype);
                }
                OkHttpManager.postAsync(HttpURL.getInstance().LOGO, "logo_code", map, this);
                break;
            case R.id.title_complete://返回键

                break;
            case R.id.title_image://注册
                intent = new Intent(LogoActivity.this, SetUpPasswordActivity.class);
                intent.putExtra("name", "1");
                startActivity(intent);
                break;
            case R.id.loan_logo_no_password://忘记密码
                intent = new Intent(LogoActivity.this, SetUpPasswordActivity.class);
                intent.putExtra("name", "2");
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void findViewById() {
        tab_activity_lin = (LinearLayout) findViewById(R.id.tab_activity_lin);
        tab_activity_lin.setBackgroundResource(R.color.transparent);
        title_image = (ImageView) findViewById(R.id.title_image);
        title_image.setVisibility(View.VISIBLE);
        title_image.setOnClickListener(this);

        title_complete = (TextView) findViewById(R.id.title_complete);
        title_complete.setVisibility(View.VISIBLE);
        title_complete.setOnClickListener(this);
        title_complete.setText(this.getString(R.string.name_loan_logo_register));

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setVisibility(View.INVISIBLE);

        loan_logo_edittext_phone = (EditText) findViewById(R.id.loan_logo_edittext_phone);
        loan_logo_edittext_code = (EditText) findViewById(R.id.loan_logo_edittext_code);

        loan_logo_button_code = (Button) findViewById(R.id.loan_logo_button_code);
        loan_logo_button_code.setOnClickListener(this);

        findViewById(R.id.loan_logo_button_logo).setOnClickListener(this);

        loan_logo_account_number = (TextView) findViewById(R.id.loan_logo_account_number);
        loan_logo_account_number.setOnClickListener(this);
        loan_logo_Re_code = (LinearLayout) findViewById(R.id.loan_logo_Re_code);
        loan_logo_Re_password = (RelativeLayout) findViewById(R.id.loan_logo_Re_password);

        loan_logo_no_password = (TextView) findViewById(R.id.loan_logo_no_password);
        loan_logo_no_password.setOnClickListener(this);

        loan_logo_edittext_password_code = (EditText) findViewById(R.id.loan_logo_edittext_password_code);

    }

    @Override
    protected void initView() {

        loan_logo_edittext_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString();
            }
        });

    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "code":
                mCountDownTimerUtils.onFinish();
                Log.e("", "====" + request + "===" + e);
                break;
            case "logo_code":
                if (logintype == 1) {

                } else {

                }
                Log.e("", "====" + request + "===" + e);
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "code":
                RegisterSignCodeModify registerSignCodeModify = JsonData.getInstance().getJsonLogoCode(result);
                if (registerSignCodeModify.getStatus() == 0) {
                    ToatUtils.showShort1(this, registerSignCodeModify.getInfo());
                } else {
                    Log.e("", "====" + result);
                }
                break;
            case "logo_code":
                if (logintype == 1) {

                } else {

                }
                Log.e("", "====" + result);
                break;

        }
    }
}
