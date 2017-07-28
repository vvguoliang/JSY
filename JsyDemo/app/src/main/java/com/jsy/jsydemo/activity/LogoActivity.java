package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.PublicClass.CountDownTimerUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/28.
 * 登录页面
 */
@SuppressLint("ResourceType")
public class LogoActivity extends FragmentActivity implements View.OnClickListener, DataCallBack {

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
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            setTranslucentStatus(true);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_activity_lin.getLayoutParams();
            lp.gravity = Gravity.CENTER;
            lp.height = DisplayUtils.px2dip(this, 48 * 8);
            ImmersiveUtils.StatusBarLightMode(this);

//            ImageView loan_logo_image = (ImageView) findViewById(R.id.loan_logo_image);
//            RelativeLayout.LayoutParams para = (RelativeLayout.LayoutParams) loan_logo_image.getLayoutParams();
//            para.height = DisplayUtils.px2dip(this, 48 * 20);
//            loan_logo_image.setLayoutParams(para);
        }
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
                    map.put("mobile", Long.parseLong(phone));
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
                map.put("username", Long.parseLong(phone));
                if (loan_logo_account_number.getText().toString().equals(LogoActivity.this.getString(logoCodeLogo))) {
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
                    map.put("code", Long.parseLong(loan_logo_edittext_code.getText().toString()));
                    map.put("password", "");
                    map.put("logintype", logintype);
                }
                OkHttpManager.postAsync(HttpURL.getInstance().LOGO, "logo_code", map, this);
                break;
            case R.id.title_complete://注册
                intent = new Intent(LogoActivity.this, SetUpPasswordActivity.class);
                intent.putExtra("name", "1");
                startActivity(intent);
                break;
            case R.id.title_image://返回键
                finish();
                break;
            case R.id.loan_logo_no_password://忘记密码
                intent = new Intent(LogoActivity.this, SetUpPasswordActivity.class);
                intent.putExtra("name", "2");
                startActivity(intent);
                break;
        }

    }

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
                    if (registerSignCodeModify.getStatus() == 1 && registerSignCodeModify.getState().equals("success")) {
                        ToatUtils.showShort1(this, registerSignCodeModify.getInfo());
                    }
                }
                break;
            case "logo_code":
                if (logintype == 1) {
                    RegisterSignCodeModify registerSignCodeModify1 = JsonData.getInstance().getJsonLogoCode(result);
                    if (registerSignCodeModify1.getStatus() == 0) {
                        ToatUtils.showShort1(this, registerSignCodeModify1.getInfo());
                    } else {
                        if (registerSignCodeModify1.getStatus() == 1 && registerSignCodeModify1.getState().equals("success")) {
                            SharedPreferencesUtils.put(this, "username", registerSignCodeModify1.getUsername());
                            SharedPreferencesUtils.put(this, "uid", registerSignCodeModify1.getUid());
                            SharedPreferencesUtils.put(this, "password", loan_logo_edittext_password_code.getText().toString());
                            finish();
                        } else {
                            ToatUtils.showShort1(this, registerSignCodeModify1.getInfo());
                        }
                    }
                } else {

                }
                Log.e("", "====" + result);
                break;

        }
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

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
