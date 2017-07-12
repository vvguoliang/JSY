package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.ContactInfo;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.TimeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/7/6.
 * 其他信息
 */

public class OtherInformationActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private LinearLayout information_mailbox_linear;
    private EditText information_mailbox_editText;
    private LinearLayout information_spouse_linear;
    private EditText information_spouse_editText;
    private LinearLayout information_live_linear;
    private EditText information_live_editText;
    private LinearLayout information_mode_linear;
    private TextView information_mode_text;
    private LinearLayout information_corporate_linear;
    private EditText information_corporate_editText;
    private LinearLayout information_corporate_address_linear;
    private EditText information_corporate_address_editText;
    private LinearLayout information_corporate_phone_linear;
    private EditText information_corporate_phone_editText;
    private Button information_please_button;
    private LinearLayout loan_personal_linear;
    private TextView other_relatives_name;
    private Button other_relatives_wathet;
    private TextView other_relatives_phone;
    private LinearLayout other_contacts_linear;
    private TextView other_contacts_name;
    private Button other_contacts_wathet;
    private TextView other_contacts_phone;

    private String other = "";

    private List<ContactInfo> result = null;

    private Boolean aBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_other_information);
        other = getIntent().getExtras().getString("other");
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.information_mode_text:

                break;
            case R.id.information_please_button:
                getHttp();
                break;
            case R.id.other_relatives_wathet:
                aBoolean = true;
                getContactID();
                break;
            case R.id.other_contacts_wathet:
                aBoolean = false;
                getContactID();
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_details_other));

        information_mailbox_linear = (LinearLayout) findViewById(R.id.information_mailbox_linear);
        information_spouse_linear = (LinearLayout) findViewById(R.id.information_spouse_linear);
        information_live_linear = (LinearLayout) findViewById(R.id.information_live_linear);
        information_mode_linear = (LinearLayout) findViewById(R.id.information_mode_linear);
        information_corporate_linear = (LinearLayout) findViewById(R.id.information_corporate_linear);
        information_corporate_address_linear = (LinearLayout) findViewById(R.id.information_corporate_address_linear);
        information_corporate_phone_linear = (LinearLayout) findViewById(R.id.information_corporate_phone_linear);
        loan_personal_linear = (LinearLayout) findViewById(R.id.loan_personal_linear);
        other_contacts_linear = (LinearLayout) findViewById(R.id.other_contacts_linear);

        information_mailbox_editText = (EditText) findViewById(R.id.information_mailbox_editText);
        information_spouse_editText = (EditText) findViewById(R.id.information_spouse_editText);
        information_live_editText = (EditText) findViewById(R.id.information_live_editText);
        information_corporate_editText = (EditText) findViewById(R.id.information_corporate_editText);
        information_corporate_address_editText = (EditText) findViewById(R.id.information_corporate_address_editText);
        information_corporate_phone_editText = (EditText) findViewById(R.id.information_corporate_phone_editText);

        information_mode_text = (TextView) findViewById(R.id.information_mode_text);
        other_relatives_name = (TextView) findViewById(R.id.other_relatives_name);
        other_relatives_phone = (TextView) findViewById(R.id.other_relatives_phone);
        other_contacts_name = (TextView) findViewById(R.id.other_contacts_name);
        other_contacts_phone = (TextView) findViewById(R.id.other_contacts_phone);

        information_please_button = (Button) findViewById(R.id.information_please_button);
        other_relatives_wathet = (Button) findViewById(R.id.other_relatives_wathet);
        other_contacts_wathet = (Button) findViewById(R.id.other_contacts_wathet);

        Pattern pattern = Pattern.compile("\\[(.*)\\]");
        Matcher matcher = pattern.matcher(other);
        String ResponseDates = "";
        while (matcher.find()) {
            ResponseDates = matcher.group(1);
        }
        String[] data_ids = ResponseDates.split(",");
        for (String data_id1 : data_ids) {
            if (data_id1.contains("1")) {
                information_mailbox_linear.setVisibility(View.VISIBLE);
            } else if (data_id1.contains("2")) {
                information_spouse_linear.setVisibility(View.VISIBLE);
            } else if (data_id1.contains("3")) {
                information_live_linear.setVisibility(View.VISIBLE);
            } else if (data_id1.contains("4")) {
                information_mode_linear.setVisibility(View.VISIBLE);
            } else if (data_id1.contains("5")) {
                information_corporate_linear.setVisibility(View.VISIBLE);
            } else if (data_id1.contains("6")) {
                information_corporate_address_linear.setVisibility(View.VISIBLE);
            } else if (data_id1.contains("7")) {
                information_corporate_phone_linear.setVisibility(View.VISIBLE);
            } else if (data_id1.contains("8")) {
                loan_personal_linear.setVisibility(View.VISIBLE);
            } else if (data_id1.contains("9")) {
                other_contacts_linear.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("email", information_mailbox_editText.getText().toString());
        map.put("mate", information_spouse_editText.getText().toString());
        map.put("dwell_address", information_live_editText.getText().toString());
        map.put("dwell_type", information_mode_text.getText().toString());
        map.put("company_name", information_corporate_editText.getText().toString());
        map.put("company_addr", information_corporate_address_editText.getText().toString());
        map.put("company_tel", information_corporate_phone_editText.getText().toString());
        map.put("user_a", other_relatives_name.getText().toString());
        map.put("relation_a", "");
        map.put("mobile_a", other_relatives_phone.getText().toString());
        map.put("user_b", other_contacts_name.getText().toString());
        map.put("relation_b", "");
        map.put("mobile_b", other_contacts_phone.getText().toString());
        OkHttpManager.postAsync(HttpURL.getInstance().BASEADD, "other", map, this);
    }

    @Override
    protected void initView() {
        information_mode_text.setOnClickListener(this);
        information_please_button.setOnClickListener(this);
        other_relatives_wathet.setOnClickListener(this);
        other_contacts_wathet.setOnClickListener(this);

    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        finish();
    }


    private void getContactID() {
        // 获取联系人数据
        ContentResolver cr = this.getContentResolver();//获取所有电话信息（而不是联系人信息），这样方便展示
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,// 姓名
                ContactsContract.CommonDataKinds.Phone.NUMBER,// 电话号码
        };
        Cursor cursor = cr.query(uri, projection, null, null, null);
        if (cursor == null) {
            return;
        }//最终要返回的数据
        result = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String number = cursor.getString(1);
            //保存到对象里
            ContactInfo info = new ContactInfo();
            info.setName(name);
            info.setNumber(number);
            //保存到集合里
            result.add(info);
        }//用完记得关闭
        cursor.close();
        if (TimeUtils.isFastDoubleClick()) {
            return;
        } else {
            if (aBoolean) {
                //弹出Toast或者Dialog
                ShowDialog.getInstance().getDialog(this, getCursor(result),
                        "cursor", mHandler, 100);
            } else {
                //弹出Toast或者Dialog
                ShowDialog.getInstance().getDialog(this, getCursor(result),
                        "cursor", mHandler, 101);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    for (int i = 0; result.size() > i; i++) {
                        if (result.get(i).getName().equals(msg.obj.toString())) {
                            other_relatives_phone.setText(result.get(i).getNumber());
                            other_relatives_name.setText(result.get(i).getName());
                        }
                    }
                    break;
                case 101:
                    for (int i = 0; result.size() > i; i++) {
                        if (result.get(i).getName().equals(msg.obj.toString())) {
                            other_contacts_phone.setText(result.get(i).getNumber());
                            other_contacts_name.setText(result.get(i).getName());
                        }
                    }
                    break;

            }
        }
    };


    private List<Map<String, Object>> getCursor(List<ContactInfo> contactInfoList) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; contactInfoList.size() > i; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", contactInfoList.get(i).getName());
            map.put("number", contactInfoList.get(i).getNumber());
            map.put("boolean", "1");
            mapList.add(map);
        }
        return mapList;
    }
}
