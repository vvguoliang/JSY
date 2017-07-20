package com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/7/20.
 * 运营商 页面
 */

public class OperatorActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private EditText operator_phone;

    private EditText operator_password;

    private TextView operator_no_password;

    private CheckBox operator_checkbox;

    private TextView operator_clausr_text;

    private Button operator_submit_button;

    private LinearLayout operator_linear;//填写数据

    private LinearLayout operator_linear1;//正在处理

    private LinearLayout operator_linear2;//结果

    private ImageView operator_logo;

    private Handler mHandler;

    private String sgin;

    private boolean checkbox = true;

    private int booNoPassword = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_operator);
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.operator_submit_button://运营商提交
                if (checkbox) {
                    operator_linear.setVisibility(View.GONE);
                    operator_linear1.setVisibility(View.VISIBLE);
                    operator_linear2.setVisibility(View.GONE);
                    booNoPassword = 1;
                    getSIGNPhone();
                } else {
                    ToatUtils.showShort1(OperatorActivity.this, "请" +
                            this.getString(R.string.operator_agree) + this.getString(R.string.operator_clause));
                }
                break;
            case R.id.title_image:
                Intent intent = new Intent();
                intent.putExtra("operator", "");
                setResult(1000, intent);
                finish();
                break;
            case R.id.operator_no_password:
                booNoPassword = 2;
                getSIGNPhone();
                break;
        }
    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.operator_grant));

        operator_phone = (EditText) findViewById(R.id.operator_phone);

        operator_password = (EditText) findViewById(R.id.operator_password);

        operator_no_password = (TextView) findViewById(R.id.operator_no_password);

        operator_checkbox = (CheckBox) findViewById(R.id.operator_checkbox);

        operator_clausr_text = (TextView) findViewById(R.id.operator_clausr_text);

        operator_submit_button = (Button) findViewById(R.id.operator_submit_button);

        operator_linear = (LinearLayout) findViewById(R.id.operator_linear);

        operator_linear1 = (LinearLayout) findViewById(R.id.operator_linear1);

        operator_linear2 = (LinearLayout) findViewById(R.id.operator_linear2);

        operator_logo = (ImageView) findViewById(R.id.operator_logo);
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.get(this, "username", "").toString())) {
            operator_phone.setText(SharedPreferencesUtils.get(this, "username", "").toString());
        }
        operator_linear.setVisibility(View.VISIBLE);
        operator_linear1.setVisibility(View.GONE);
        operator_linear2.setVisibility(View.GONE);
        operator_logo.setImageResource(R.mipmap.ic_operator_logo);
        operator_checkbox.setChecked(true);
        operator_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//选中
                    checkbox = true;
                    operator_submit_button.setClickable(true);
                    operator_submit_button.setBackgroundResource(R.mipmap.ic_set_up_confirm_button);
                } else {//未选中
                    checkbox = false;
                    operator_submit_button.setClickable(false);
                    operator_submit_button.setBackgroundResource(R.mipmap.ic_set_up_no_confirm_button);
                }
            }
        });
        operator_submit_button.setOnClickListener(this);
        operator_no_password.setOnClickListener(this);
        mHandler = new Handler();
    }

    private void getSIGNPhone() {
        Map<String, Object> map = new HashMap<>();
        map.put("apiKey", "0618854278903691");
        map.put("version", "1.0.0");
        map.put("method", "api.mobile.area");
        map.put("mobileNo", SharedPreferencesUtils.get(this, "username", "").toString());
        OkHttpManager.postAsync(HttpURL.getInstance().SIGN, "product_phone", map, this);
    }

    private void getProductHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("apiKey", "0618854278903691");
        map.put("version", "1.0.0");
        map.put("method", "api.mobile.area");
        map.put("sign", sgin);
        map.put("mobileNo", SharedPreferencesUtils.get(this, "username", "").toString());
        OkHttpManager.postAsync(HttpURL.getInstance().HTTP_OPERATOR, "product_http", map, this);
    }

    private void getOperator() {
        Map<String, Object> map = new HashMap<>();
        map.put("apiKey", "0618854278903691");
        map.put("version", "1.0.0");
        map.put("sign", sgin);
        map.put("method", "api.mobile.area");
        map.put("identityCardNo", SharedPreferencesUtils.get(this, "idcard", "").toString());
        map.put("identityName", SharedPreferencesUtils.get(this, "realname", "").toString());
        map.put("username", SharedPreferencesUtils.get(this, "username", "").toString());
        map.put("password", Base64.encodeToString(operator_password.getText().toString().getBytes(), Base64.DEFAULT));
        map.put("contentType", "alls");
        OkHttpManager.postAsync(HttpURL.getInstance().HTTP_OPERATOR, "product_http", map, this);
    }

    private void getSIGN() {
        Map<String, Object> map = new HashMap<>();
        map.put("apiKey", "0618854278903691");
        map.put("version", "1.0.0");
        map.put("method", "api.mobile.area");
        map.put("identityCardNo", SharedPreferencesUtils.get(this, "idcard", "").toString());
        map.put("identityName", SharedPreferencesUtils.get(this, "realname", "").toString());
        map.put("username", SharedPreferencesUtils.get(this, "username", "").toString());
        map.put("password", Base64.encodeToString(operator_password.getText().toString().getBytes(), Base64.DEFAULT));
        map.put("contentType", "alls");
        OkHttpManager.postAsync(HttpURL.getInstance().SIGN, "product_sign", map, this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {

    }

    /**
     * 每1S Handler 发送一次 直到收到结果
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getProductHttp();
        }
    };

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        JSONObject object1;
        switch (name) {
            case "product_phone":
                JSONObject object2 = new JSONObject(result);
                object1 = new JSONObject(object2.optString("data"));
                sgin = object1.optString("sign");
                getProductHttp();
                break;
            case "product_sign":
                getOperator();
                break;
            case "product_http":
                if (TextUtils.isEmpty(result)) {
                    mHandler.postAtTime(runnable, 1000);
                } else {
                    mHandler.removeCallbacks(runnable);
                    JSONObject object = new JSONObject(result);
                    String code = object.optString("code");
                    String type = object.optString("type");
                    String areacode = object.optString("areacode");
                    if(booNoPassword == 1){//提交

                    }else{//忘记密码


                    }
                }
                break;
        }
    }


}
