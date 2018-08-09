package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jsy.jsydemo.EntityClass.RegisterSignCodeModify;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.PublicClass.CountDownTimerUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * @Time : 2018/8/8 no 10:17
 * @USER : vvguoliang
 * @File : LogoActivity1.java
 * @Software: Android Studio
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 * ***┏┓   ┏ ┓
 * **┏┛┻━━━┛ ┻┓
 * **┃        ┃
 * **┃ ┳┛  ┗┳ ┃
 * **┃    ┻   ┃
 * **┗━┓    ┏━┛
 * ****┃    ┗━━━┓
 * ****┃ 神兽保佑 ┣┓
 * ****┃ 永无BUG！┏┛
 * ****┗┓┓┏━┳┓┏┛┏┛
 * ******┃┫┫  ┃┫┫
 * ******┗┻┛  ┗┻┛
 */
@SuppressLint("Registered")
public class LogoActivity1 extends FragmentActivity implements View.OnClickListener, DataCallBack {

    private LinearLayout tab_activity_lin;

    private EditText loan_logo_edittext_phone;

    private EditText loan_logo_edittext_code;

    private Button loan_logo_button_code;

    private Button loan_logo_button_logo;

    private CountDownTimerUtils mCountDownTimerUtils;

    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_logo1);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            setTranslucentStatus(true);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_activity_lin.getLayoutParams();
            lp.gravity = Gravity.CENTER;
            lp.height = DisplayUtils.px2dip(this, 48 * 10) + 120;
            ImmersiveUtils.stateBarTint(this, "#00000000", true, false);
            //设置状态栏白色字体
            ImmersiveUtils.StatusFragmentBarDarkMode(this);
        }
        initView();
    }

    protected void findViewById() {
        tab_activity_lin = findViewById(R.id.tab_activity_lin);
        tab_activity_lin.setBackgroundResource(R.color.transparent);
        findViewById(R.id.title_image).setVisibility(View.GONE);
        findViewById(R.id.title_view).setVisibility(View.GONE);

        loan_logo_edittext_phone = findViewById(R.id.loan_logo_edittext_phone);
        loan_logo_edittext_code = findViewById(R.id.loan_logo_edittext_code);
        loan_logo_button_code = findViewById(R.id.loan_logo_button_code);
        loan_logo_button_logo = findViewById(R.id.loan_logo_button_logo);
    }

    protected void initView() {

        loan_logo_button_code.setOnClickListener(this);
        loan_logo_button_logo.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
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
                    map.put("source", AppUtil.SOURCE);
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
                String no = AppUtil.getInstance().getChannel(LogoActivity1.this, 2) + "";
                map.put("no", no);//渠道号
//                if (loan_logo_account_number.getText().toString().equals(LogoActivity1.this.getString(logoCodeLogo))) {
//                    logintype = 1;
//                    if (StringUtil.isNullOrEmpty(loan_logo_edittext_password_code.getText().toString())) {
//                        ToatUtils.showShort1(this, "请输入密码");
//                        return;
//                    }
//                    map.put("code", 0);
//                    map.put("logintype", logintype);
//                    map.put("password", loan_logo_edittext_password_code.getText().toString());
//                } else {
//                    logintype = 2;
                if (StringUtil.isNullOrEmpty(loan_logo_edittext_code.getText().toString())) {
                    ToatUtils.showShort1(this, "请输入验证码");
                    return;
                }
                map.put("code", Long.parseLong(loan_logo_edittext_code.getText().toString()));
                map.put("password", "");
                map.put("logintype", 2);
//                }
                map.put("version", AppUtil.getInstance().getVersionName(1, this));//后台区分标识符
                OkHttpManager.postAsync(HttpURL.getInstance().LOGO, "logo_code", map, this);
//                OkHttpManager.postAsync(HttpURL.getInstance().logoinString, "logo_code", map, this);
                break;
        }
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "code":
                mCountDownTimerUtils.onFinish();
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
            case "logo_code":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
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
//                if (logintype == 1) {
//                    RegisterSignCodeModify registerSignCodeModify1 = JsonData.getInstance().getJsonLogoCode(result);
//                    if (registerSignCodeModify1.getStatus() == 0) {
//                        ToatUtils.showShort1(this, registerSignCodeModify1.getInfo());
//                    } else {
//                        if (registerSignCodeModify1.getStatus() == 1 && registerSignCodeModify1.getState().equals("success")) {
//                            SharedPreferencesUtils.put(this, "username", registerSignCodeModify1.getUsername());
//                            SharedPreferencesUtils.put(this, "uid", registerSignCodeModify1.getUid());
//                            SharedPreferencesUtils.put(this, "password", loan_logo_edittext_password_code.getText().toString());
//                            startActivity(new Intent(this, MainActivity.class));
//                            finish();
//                        } else {
//                            ToatUtils.showShort1(this, registerSignCodeModify1.getInfo());
//                        }
//                    }
//                } else {
                RegisterSignCodeModify registerSignCodeModify1 = JsonData.getInstance().getJsonLogoCode(result);
                if (registerSignCodeModify1.getStatus() == 0) {
                    ToatUtils.showShort1(this, registerSignCodeModify1.getInfo());
                } else {
                    if (registerSignCodeModify1.getStatus() == 1 && registerSignCodeModify1.getState().equals("success")) {
                        SharedPreferencesUtils.put(this, "username", registerSignCodeModify1.getUsername());
                        SharedPreferencesUtils.put(this, "uid", registerSignCodeModify1.getUid());
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        ToatUtils.showShort1(this, registerSignCodeModify1.getInfo());
                    }
                }
//                }
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
