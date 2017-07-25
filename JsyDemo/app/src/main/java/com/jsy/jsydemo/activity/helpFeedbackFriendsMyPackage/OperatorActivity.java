package com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.base.BaseActivityManager;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CameraUtils.UserCenterRealize;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.PublicEditTextDialog;
import com.jsy.jsydemo.view.PublicPhoneDialog;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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

    private String sgin;

    private boolean checkbox = true;

    private int booNoPassword = 0;

    private String otherInfo = "";

    private Intent intent = null;

    private UserCenterRealize userCenterRealize = new UserCenterRealize();

    private String type = "";

    private String isID = "isID";

    private boolean booType = false;

    private Handler handler = null;

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
                    if (TextUtils.isEmpty(operator_password.getText().toString())) {
                        booNoPassword = 2;
                    } else {
                        booNoPassword = 1;
                        operator_linear.setVisibility(View.GONE);
                        operator_linear1.setVisibility(View.VISIBLE);
                        operator_linear2.setVisibility(View.GONE);
                    }
                    getSIGNPhone();
                } else {
                    ToatUtils.showShort1(OperatorActivity.this, "请" +
                            this.getString(R.string.operator_agree) + this.getString(R.string.operator_clause));
                }
                break;
            case R.id.title_image:
                intent = new Intent();
                intent.putExtra("operator", "2");
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
        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (booType) {
            ShowDialog.getInstance().getEdiText(this, "北京移动需要输入客服密码", "客服密码:", 1000, isID, mHandler);
        }
    }

    /**
     * 请求服务器
     */
    private void getSIGNPhone() {
        Map<String, Object> map = new HashMap<>();
        map.put("apiKey", "0618854278903691");
        map.put("version", "1.0.0");
        map.put("method", "api.mobile.area");
        map.put("mobileNo", SharedPreferencesUtils.get(this, "username", "").toString());
        OkHttpManager.postAsync(HttpURL.getInstance().SIGN, "product_phone", map, this);
    }

    /**
     * 请求探知数据 手机号
     */
    private void getProductHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("apiKey", "0618854278903691");
        map.put("version", "1.0.0");
        map.put("method", "api.mobile.area");
        map.put("sign", sgin);
        map.put("mobileNo", SharedPreferencesUtils.get(this, "username", "").toString());
        OkHttpManager.postAsync(HttpURL.getInstance().HTTP_OPERATOR, "product_http", map, this);
    }

    private void getSINGOPerator() {
        if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "idcard", "").toString())) {
            ShowDialog.getInstance().getEdiText(this, "需要输入身份证号", "身份证号:", 1004, "idcard", mHandler);
        } else if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "realname", "").toString())) {
            ShowDialog.getInstance().getEdiText(this, "需要输入姓名", "姓名:", 1004, "realname", mHandler);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("apiKey", "0618854278903691");
        map.put("version", "1.0.0");
        map.put("method", "api.mobile.area");
        map.put("identityCardNo", SharedPreferencesUtils.get(this, "idcard", "").toString());
        try {
            map.put("identityName", URLEncoder.encode(
                    SharedPreferencesUtils.get(this, "realname", "").toString(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("username", SharedPreferencesUtils.get(this, "username", "").toString());
        map.put("password", Base64.encodeToString(operator_password.getText().toString().getBytes(), Base64.DEFAULT));
        map.put("contentType", "busi");
        map.put("otherInfo", otherInfo);
        OkHttpManager.postAsync(HttpURL.getInstance().SIGN, "product_phone_content", map, this);
    }

    /**
     * 请求探知手机归属地
     */
    private void getOperator() {
        Map<String, Object> map = new HashMap<>();
        map.put("apiKey", "0618854278903691");
        map.put("version", "1.0.0");
        map.put("method", "api.mobile.area");
        map.put("sign", sgin);
        map.put("identityCardNo", SharedPreferencesUtils.get(this, "idcard", "").toString());
        try {
            map.put("identityName", URLEncoder.encode(
                    SharedPreferencesUtils.get(this, "realname", "").toString(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("username", SharedPreferencesUtils.get(this, "username", "").toString());
        map.put("password", Base64.encodeToString(operator_password.getText().toString().getBytes(), Base64.DEFAULT));
        map.put("contentType", "busi");
        map.put("otherInfo", otherInfo);
        OkHttpManager.postAsync(HttpURL.getInstance().HTTP_OPERATOR, "product_content_content", map, this);
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
        JSONObject object;
        switch (name) {
            case "product_phone":
                object = new JSONObject(result);
                object = new JSONObject(object.optString("data"));
                sgin = object.optString("sign");
                getProductHttp();
                break;
            case "product_http":
                if (TextUtils.isEmpty(result)) {
                    handler.postAtTime(runnable, 1000);
                } else {
                    handler.removeCallbacks(runnable);
                    object = new JSONObject(result);
                    type = object.optString("type");
                    String province = object.optString("province");
                    if (object.optString("code").equals("0000")) {
                        if (booNoPassword == 1) {//提交
                            switch (province) {
                                case "北京":
                                    if (type.equals("移动")) {
                                        booType = true;
                                        ShowDialog.getInstance().getEdiText(this, "北京移动需要输入客服密码",
                                                "客服密码:", 1000, isID, mHandler);
                                    }
                                    break;
                                case "广西":
                                    if (type.equals("电信")) {
                                        if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "idcard", "").toString())) {
                                            ShowDialog.getInstance().getEdiText(this, "广西电信需要输入身份证号", "身份证号:",
                                                    1001, "idcard", mHandler);
                                        } else {
                                            otherInfo = SharedPreferencesUtils.get(this, "idcard", "").toString();
                                        }
                                    }
                                    break;
                                case "山西":
                                    if (type.equals("电信")) {
                                        if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "idcard", "").toString())) {
                                            ShowDialog.getInstance().getEdiText(this, "山西电信需要输入身份证号", "身份证号:",
                                                    1002, "idcard", mHandler);
                                        } else {
                                            otherInfo = SharedPreferencesUtils.get(this, "idcard", "").toString();
                                        }
                                    }
                                    break;
                                case "吉林":
                                    if (type.equals("电信")) {
                                        ShowDialog.getInstance().getEdiText(this, "吉林电信需要编辑短信'CXXD'\n发送给1001获取验证码",
                                                "短信验证码:", 1003, "code", mHandler);
                                    }
                                    break;
                                default:
                                    if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "idcard", "").toString())) {
                                        ShowDialog.getInstance().getEdiText(this, "需要输入身份证号", "身份证号:", 1004,
                                                "idcard", mHandler);
                                    } else if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "realname", "").
                                            toString())) {
                                        ShowDialog.getInstance().getEdiText(this, "需要输入姓名", "姓名:", 1004,
                                                "realname", mHandler);
                                    }
                            }
                        } else {//忘记密码
                            getType2();
                        }
                    }
                }
                break;
            case "product_content_content":
                Log.e("", "");
                break;
            case "product_phone_content":
                object = new JSONObject(result);
                object = new JSONObject(object.optString("data"));
                sgin = object.optString("sign");
                getOperator();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if (msg.obj.toString().equals("1")) {
                        ShowDialog.getInstance().getEdiText(OperatorActivity.this, "北京移动需要输入客服密码",
                                "客服密码:", 1000, isID, mHandler);
                    } else {
                        if (TextUtils.isEmpty(msg.obj.toString())) {
                            getPhone("移动客服电话", "请打客服" + AppUtil.getInstance().PHONE_MOVE + "电话,进行咨询客服密码",
                                    AppUtil.getInstance().PHONE_MOVE);
                        } else {
                            booType = false;
                            otherInfo = msg.obj.toString();
                            getSINGOPerator();
                        }
                    }
                    break;
                case 1001:
                    if (msg.obj.toString().equals("1")) {
                        ShowDialog.getInstance().getEdiText(OperatorActivity.this, "广西电信需要输入身份证号", "身份证号:",
                                1001, "idcard", mHandler);
                    } else {
                        otherInfo = msg.obj.toString();
                        getOperator();
                    }
                    break;
                case 1002:
                    if (msg.obj.toString().equals("1")) {
                        ShowDialog.getInstance().getEdiText(OperatorActivity.this, "山西电信需要输入身份证号", "身份证号:",
                                1002, "idcard", mHandler);
                    } else {
                        otherInfo = msg.obj.toString();
                        getOperator();
                    }
                    break;
                case 1003:
                    if (msg.obj.toString().equals("1")) {
                        ShowDialog.getInstance().getEdiText(OperatorActivity.this, "吉林电信需要编辑短信'CXXD'\n发送给1001获取验证码",
                                "短信验证码:", 1003, "", mHandler);
                    } else {
                        otherInfo = msg.obj.toString();
                        getOperator();
                    }
                    break;
                case 1004:
                    getOperator();
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_PHONE_DIAL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getType2();
            } else {
                Toast.makeText(this, "请授予拨打电话权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getType2() {
        switch (type) {
            case "移动":
                getPhone("移动客服电话", "请打客服" + AppUtil.getInstance().PHONE_MOVE + "电话,进行咨询服务密码",
                        AppUtil.getInstance().PHONE_MOVE);
                break;
            case "联通":
                getPhone("联通客服电话", "请打客服" + AppUtil.getInstance().PHONE_UNICOM + "电话,进行咨询服务密码",
                        AppUtil.getInstance().PHONE_UNICOM);
                break;
            case "电信":
                getPhone("电信客服电话", "请打客服" + AppUtil.getInstance().PHONE_TELECOM + "电话,进行咨询服务密码",
                        AppUtil.getInstance().PHONE_TELECOM);
                break;
        }
    }

    private void getPhone(String title, String msg, final String phone) {
        PublicPhoneDialog.Builder builder = new PublicPhoneDialog.Builder(this);
        builder.setTitle(title);
        builder.setTiltleMsg(msg);
        builder.setContentViewCancel("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setContentViewDetermine("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userCenterRealize.getPhoneDial(OperatorActivity.this, phone);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            intent.putExtra("operator", "2");
            setResult(1000, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
