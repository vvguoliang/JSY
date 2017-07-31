package com.jsy.jsydemo.activity.personaldata;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.OperatorActivity;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.PublicPhoneDialog;
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

    private String operator = "";

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_operator);
        operator = getIntent().getExtras().getString("operator");
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
            case R.id.operator_no_authorization_linear:
            case R.id.operator_no_authorization:
                getUserDataHttp();
                break;
            case R.id.title_image:
                intent = new Intent();
                intent.putExtra("complete", "2");
                setResult(106, intent);
                finish();
                break;
            case R.id.title_complete:
                intent = new Intent();
                intent.putExtra("complete", "1");
                setResult(106, intent);
                finish();
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
        TextView title_view = findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_operator));

        operator_no_authorization = findViewById(R.id.operator_no_authorization);
        operator_no_authorization.setOnClickListener(this);
        if (operator.equals(this.getString(R.string.name_loan_personal_data_complete))) {
            operator_no_authorization.setText(this.getString(R.string.name_loan_operator_authorization));
        } else {
            operator_no_authorization.setText(this.getString(R.string.name_loan_operator_no_authorization));
        }

        findViewById(R.id.operator_no_authorization_linear).setOnClickListener(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            String datastring = data.getExtras().getString("operator");
            if (!TextUtils.isEmpty(datastring)) {
                if (datastring.equals("1")) {
                    operator_no_authorization.setText(this.getString(R.string.name_loan_operator_authorization));
                } else {
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

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "user_data":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
        }

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "user_data":
                JSONObject object = new JSONObject(result);
                code = object.optString("code");
                if (code.equals("0000")) {
                    startActivityForResult(new Intent(PersonalDataOperatorActivity.this,
                            OperatorActivity.class), 1000);
                } else if (code.equals("0002")) {
                    getPhone("提示", object.optString("msg"));
                }
                break;
        }
    }

    private void getPhone(String title, String msg) {
        PublicPhoneDialog.Builder builder = new PublicPhoneDialog.Builder(this);
        builder.setTitle(title);
        builder.setTiltleMsg(msg);
        builder.setContentViewDetermine("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            intent.putExtra("complete", "2");
            setResult(106, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
