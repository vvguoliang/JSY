package com.jsy.jsydemo.activity.personaldata;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.ContactInfo;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.TimeUtils;
import com.umeng.analytics.MobclickAgent;

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

    private TextView other_relatives_name;

    private TextView other_relatives_phone;

    private TextView other_contacts_name;

    private TextView other_contacts_phone;

    private boolean aBoolean = false;

    private List<ContactInfo> result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_data_other);
        findViewById();
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
        findViewById(R.id.title_complete).setVisibility(View.VISIBLE);
        findViewById(R.id.title_complete).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_data_other));

        findViewById(R.id.other_relatives_wathet).setOnClickListener(this);
        findViewById(R.id.other_contacts_wathet).setOnClickListener(this);

        other_relatives_name = (TextView) findViewById(R.id.other_relatives_name);
        other_relatives_phone = (TextView) findViewById(R.id.other_relatives_phone);
        other_contacts_name = (TextView) findViewById(R.id.other_contacts_name);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Uri uri = data.getData();
                String[] contacts = getPhoneContacts(uri);
                if (aBoolean) {
                    other_relatives_name.setText(contacts[0]);
                    other_relatives_phone.setText(contacts[1]);
                } else {
                    other_contacts_name.setText(contacts[0]);
                    other_contacts_phone.setText(contacts[1]);
                }
            }
        }
    }

    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone != null) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "other_list":
                break;
            case "other_add":
                break;
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "other_list":
                List<Map<String, String>> maps = JsonData.getInstance().getJsonPersonalDataOther(result);
                other_relatives_name.setText(maps.get(0).get("kinsfolk_name"));
                other_relatives_phone.setText(maps.get(0).get("kinsfolk_mobile"));
                other_contacts_name.setText(maps.get(0).get("urgency_name"));
                other_contacts_phone.setText(maps.get(0).get("urgency_mobile"));
                break;
            case "other_add":
                finish();
                break;
        }
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
