package com.jsy.jsydemo.activity.SetUp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.RegisterSignCodeModify;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
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
 * <p>
 * 修改密码
 */

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
public class SetUpPasswordActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private EditText password_phone;

    private EditText password_edittext_code;

    private Button password_button_code;

    private EditText password_edittext_pass;

    private EditText password_edittext_pass_confirm;

    private CountDownTimerUtils mCountDownTimerUtils;

    private Button password_button_confirm;

    private String name = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set_up_password);
        name = getIntent().getExtras().getString("name");
        findViewById();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.password_button_code:
                if (StringUtil.isNullOrEmpty(password_phone.getText().toString())) {
                    ToatUtils.showShort1(this, "您输入的手机号码不能为空");
                } else if (!StringUtil.isMobileNO(password_phone.getText().toString())) {
                    ToatUtils.showShort1(this, "您输入的手机号码不正确,请重新输入");
                    password_phone.setText("");
                } else {
                    mCountDownTimerUtils = new CountDownTimerUtils(password_button_code, 60 * 1000, 1000);
                    mCountDownTimerUtils.start();
                    Map<String, Object> map = new HashMap<>();
                    map.put("mobile", (int) Double.parseDouble(password_phone.getText().toString()));
                    map.put("password", "");
                    map.put("code", 0);
                    OkHttpManager.postAsync(HttpURL.getInstance().REGISTER_CODE, "code", map, this);
                }
                break;
            case R.id.password_button_confirm:
                if (StringUtil.isNullOrEmpty(password_phone.getText().toString())) {
                    ToatUtils.showShort1(this, "您输入的手机号码不能为空");
                } else if (!StringUtil.isMobileNO(password_phone.getText().toString())) {
                    ToatUtils.showShort1(this, "您输入的手机号码不正确,请重新输入");
                    password_phone.setText("");
                } else if (StringUtil.isNullOrEmpty(password_button_code.getText().toString())) {
                    ToatUtils.showShort1(this, "请输入验证码");
                } else if (StringUtil.isNullOrEmpty(password_edittext_pass.getText().toString())) {
                    ToatUtils.showShort1(this, "您输入的新密码为空");
                } else if (StringUtil.isNullOrEmpty(password_edittext_pass_confirm.getText().toString())) {
                    ToatUtils.showShort1(this, "您输入的确认密码为空");
                } else if (!password_edittext_pass.getText().toString().equals(password_edittext_pass_confirm.getText())) {
                    ToatUtils.showShort1(this, "您输入的新密码和确认密码不一致");
                    password_edittext_pass.setText("");
                    password_edittext_pass_confirm.setText("");
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("mobile", (int) Double.parseDouble(password_phone.getText().toString()));
                    map.put("password", password_edittext_pass_confirm.getText());
                    map.put("code", (int) Double.parseDouble(password_edittext_code.getText().toString()));
                    if (name.equals("1")) {
                        map.put("no", AppUtil.getInstance().getChannel(SetUpPasswordActivity.this, 2));
                        OkHttpManager.postAsync(HttpURL.getInstance().REGISTER, "register", map, this);
                    } else
                        OkHttpManager.postAsync(HttpURL.getInstance().PASSWORD, "password", map, this);
                }
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        password_phone = (EditText) findViewById(R.id.password_phone);
        password_edittext_code = (EditText) findViewById(R.id.password_edittext_code);
        password_edittext_pass = (EditText) findViewById(R.id.password_edittext_pass);
        password_edittext_pass_confirm = (EditText) findViewById(R.id.password_edittext_pass_confirm);

        password_button_code = (Button) findViewById(R.id.password_button_code);
        password_button_code.setOnClickListener(this);
        password_button_confirm = (Button) findViewById(R.id.password_button_confirm);
        password_button_confirm.setOnClickListener(this);
        if (name.equals("1")) {
            title_view.setText(this.getString(R.string.name_loan_logo_register));
            password_button_confirm.setText(this.getString(R.string.name_loan_logo_register));
            findViewById(R.id.agreement_password).setVisibility(View.VISIBLE);
        } else {
            title_view.setText(this.getString(R.string.name_loan_set_up_password));
            password_button_confirm.setText(this.getString(R.string.name_loan_personal_data_complete));
            findViewById(R.id.agreement_password).setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "code":
                mCountDownTimerUtils.onFinish();
                Log.e("", "====" + request + "===" + e);
                break;
            case "password":
                break;
            case "register":
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
            case "password":
                Log.e("", "====" + result);
                break;
            case "register":
                Log.e("", "====" + result);
                break;
        }
    }
}
