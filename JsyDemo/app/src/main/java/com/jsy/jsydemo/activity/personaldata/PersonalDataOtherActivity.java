package com.jsy.jsydemo.activity.personaldata;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsy.jsydemo.EntityClass.ContactInfo;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CameraUtils.UserCenterRealize;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.TimeUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/27.
 * 其他联系人
 */

public class PersonalDataOtherActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private EditText other_relatives_name;

    private TextView other_relatives_phone;

    private EditText other_contacts_name;

    private TextView other_contacts_phone;

    private UserCenterRealize userCenterRealize = new UserCenterRealize();

    private int id = 0;

    private String[] get = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_other);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            LinearLayout tab_activity_lin = (LinearLayout) findViewById(R.id.tab_activity_lin);
            stateBarTint("#305591", true);
            statusFragmentBarDarkMode();
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_activity_lin.getLayoutParams();
            lp.height = DisplayUtils.px2dip(this, 48 * 13);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.title_complete:
                getHttpCredit();
                break;
            case R.id.other_relatives_wathet:
                id = 1;
                userCenterRealize.startPhone(PersonalDataOtherActivity.this, mHandler, id);
                break;
            case R.id.other_contacts_wathet:
                id = 2;
                userCenterRealize.startPhone(PersonalDataOtherActivity.this, mHandler, id);
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
        title_view.setText(this.getString(R.string.name_loan_personal_data_other));

        findViewById(R.id.other_relatives_wathet).setOnClickListener(this);
        findViewById(R.id.other_contacts_wathet).setOnClickListener(this);

        other_relatives_name = (EditText) findViewById(R.id.other_relatives_name);
        other_relatives_phone = (TextView) findViewById(R.id.other_relatives_phone);
        other_contacts_name = (EditText) findViewById(R.id.other_contacts_name);
        other_contacts_phone = (TextView) findViewById(R.id.other_contacts_phone);

        findViewById(R.id.loan_personal_linear).setVisibility(View.VISIBLE);
        findViewById(R.id.other_contacts_linear).setVisibility(View.VISIBLE);

        getHttp();

    }

    @Override
    protected void initView() {

    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        OkHttpManager.postAsync(HttpURL.getInstance().OTHERLIST, "other_list", map, this);
    }

    private void getHttpCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("kinsfolk_name", other_relatives_name.getText().toString());
        map.put("kinsfolk_mobile", other_relatives_phone.getText().toString());
        map.put("urgency_name", other_contacts_name.getText().toString());
        map.put("urgency_mobile", other_contacts_phone.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().OTHERADD, "other_add", map, this);
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
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "other_list":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
            case "other_add":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "other_list":
                List<Map<String, String>> maps = JsonData.getInstance().getJsonPersonalDataOther(result);
                if (maps != null && maps.size() > 0) {
                    other_relatives_name.setText(maps.get(0).get("kinsfolk_name"));
                    other_relatives_phone.setText(maps.get(0).get("kinsfolk_mobile"));
                    other_contacts_name.setText(maps.get(0).get("urgency_name"));
                    other_contacts_phone.setText(maps.get(0).get("urgency_mobile"));
                }
                break;
            case "other_add":
                JSONObject object = new JSONObject(result);
                if (object.optString("code").equals("0000")) {
                    finish();
                } else {
                    ToatUtils.showShort1(this, object.optString("msg"));
                }
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    get = msg.obj.toString().split(",");
                    other_relatives_phone.setText(get[1]);
                    other_relatives_name.setText(get[0]);
                    break;
                case 101:
                    get = msg.obj.toString().split(",");
                    other_contacts_phone.setText(get[1]);
                    other_contacts_name.setText(get[0]);
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getContactID(PersonalDataOtherActivity.this, mHandler, id);
            } else {
                Toast.makeText(this, "请授予联系人权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
