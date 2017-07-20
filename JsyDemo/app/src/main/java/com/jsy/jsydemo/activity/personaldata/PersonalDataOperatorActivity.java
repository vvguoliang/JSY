package com.jsy.jsydemo.activity.personaldata;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.OperatorActivity;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/27.
 * <p>
 * 运营商验证
 */

public class PersonalDataOperatorActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private TextView operator_no_authorization;

    private String code = "";

    private String msg = "";

    private boolean booMsg = false;

    private int aoth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_operator);
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.operator_no_authorization_linear:
            case R.id.operator_no_authorization:
                startActivityForResult(new Intent(PersonalDataOperatorActivity.this,
                        OperatorActivity.class), 1000);
                break;
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                if (booMsg) {
                    getUserDataHttp();
                } else {
                    getUserDataAothHttp();
                }
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
        title_view.setText(this.getString(R.string.name_loan_personal_data_operator));

        operator_no_authorization = (TextView) findViewById(R.id.operator_no_authorization);
        operator_no_authorization.setOnClickListener(this);

        findViewById(R.id.operator_no_authorization_linear).setOnClickListener(this);
    }

    @Override
    protected void initView() {
        getUserDataHttp();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            String datastring = data.getExtras().getString("operator");
            if (!TextUtils.isEmpty(datastring)) {
                if (datastring.equals("1")) {
                    aoth = 1;
                    operator_no_authorization.setText(this.getString(R.string.name_loan_operator_authorization));
                } else {
                    aoth = 2;
                    operator_no_authorization.setText(this.getString(R.string.name_loan_operator_no_authorization));
                }
            }
        }
    }

    private void getUserDataHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedPreferencesUtils.get(this, "uid", "").toString());
        OkHttpManager.postAsync(HttpURL.getInstance().USERDATAIL, "user_data", map, this);
    }

    private void getUserDataAothHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "", "").toString()));
        map.put("auth", aoth);
        OkHttpManager.postAsync(HttpURL.getInstance().USERDATAILAUTH, "user_auth", map, this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "user_auth":
                Log.e("", "");
                break;
            case "user_data":
                Log.e("", "");
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "user_auth":
                Log.e("", "");
                break;
            case "user_data":
                Log.e("", "");
                JSONObject object = new JSONObject(result);
                code = object.optString("code");
                msg = object.optString("msg");
                if (code.equals("0002")) {
                    booMsg = true;
                } else {
                    booMsg = false;
                    operator_no_authorization.setText(this.getString(R.string.name_loan_operator_authorization));
                    getUserDataAothHttp();
                }
                break;
        }
    }
}
