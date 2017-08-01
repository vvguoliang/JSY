package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CameraUtils.UserCenterRealize;
import com.jsy.jsydemo.utils.IdcardValidator;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.PublicClass.ShowDialog;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.TimeUtils;
import com.jsy.jsydemo.utils.ToatUtils;

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

@SuppressWarnings("StatementWithEmptyBody")
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

    private String[] mode_Strings = new String[]{"有住房,无贷款", "有住房,有贷款", "与父母/配偶同住", "租房同住", "单位宿舍/用房", "学生公寓"};

    private UserCenterRealize userCenterRealize = new UserCenterRealize();

    private int id = 0;

    private String[] get = null;

    private String pid = "";

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_other_information);
        other = getIntent().getExtras().getString("other");
        pid = getIntent().getExtras().getString("pid");
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B(this);
        }
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                intent = new Intent();
                intent.putExtra("operator", "2");
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
            case R.id.information_mode_linear:
            case R.id.information_mode_text:
                if (TimeUtils.isFastDoubleClick()) {
                    return;
                } else {
                    ShowDialog.getInstance().getDialog(this, getmode(), "mode", mHandler, 1000);
                }
                break;
            case R.id.information_please_button:
                if (information_mailbox_linear.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(information_mailbox_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入邮箱");
                        return;
                    } else if (!IdcardValidator.getInstance().isEmail(information_mailbox_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "输入邮箱不正确");
                        information_mailbox_editText.setText("");
                        return;
                    }
                }
                if (information_spouse_linear.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(information_spouse_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入配偶名字");
                        return;
                    } else if (information_spouse_editText.getText().toString().length() < 2) {
                        ToatUtils.showShort1(this, "输入配偶名字不正确");
                        information_spouse_editText.setText("");
                        return;
                    }
                }
                if (information_live_linear.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(information_live_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入详细地址");
                        return;
                    } else if (information_live_editText.getText().toString().length() < 5) {
                        ToatUtils.showShort1(this, "请输入详细地址");
                        information_live_editText.setText("");
                        return;
                    }
                }
                if (information_mode_linear.getVisibility() == View.VISIBLE) {
                    if (information_mode_text.getText().toString().equals(this.getString(R.string.name_loan_credit_please_select))) {
                        ToatUtils.showShort1(this, "请选择居住方式");
                        return;
                    }
                }
                if (information_corporate_linear.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(information_corporate_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入公司名字");
                        return;
                    }
                }
                if (information_corporate_address_linear.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(information_corporate_address_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入公司地址");
                        return;
                    }
                }
                if (information_corporate_phone_linear.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(information_corporate_phone_editText.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入公司电话");
                        return;
                    }
                }
                if (loan_personal_linear.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(other_relatives_name.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入联系人姓名");
                        return;
                    } else if (TextUtils.isEmpty(other_relatives_phone.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入联系人电话");
                        return;
                    } else if (IdcardValidator.getInstance().isIdcard(other_relatives_phone.getText().toString().trim())) {
                        ToatUtils.showShort1(this, "请输入联系人电话有误");
                        other_relatives_phone.setText("");
                        return;
                    }
                }
                if (other_contacts_linear.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(other_contacts_name.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入联系人姓名");
                        return;
                    } else if (TextUtils.isEmpty(other_contacts_phone.getText().toString())) {
                        ToatUtils.showShort1(this, "请输入联系人电话");
                        return;
                    } else if (IdcardValidator.getInstance().isIdcard(other_contacts_phone.getText().toString().trim())) {
                        ToatUtils.showShort1(this, "请输入联系人电话有误");
                        other_contacts_phone.setText("");
                        return;
                    }
                }
                getHttp();
                break;
            case R.id.other_relatives_wathet:
                id = 1;
                userCenterRealize.startPhone(OtherInformationActivity.this, mHandler, id);
                break;
            case R.id.other_contacts_wathet:
                id = 2;
                userCenterRealize.startPhone(OtherInformationActivity.this, mHandler, id);
                break;
        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_details_other));

        information_mailbox_linear = findViewById(R.id.information_mailbox_linear);
        information_spouse_linear = findViewById(R.id.information_spouse_linear);
        information_live_linear = findViewById(R.id.information_live_linear);
        information_mode_linear = findViewById(R.id.information_mode_linear);
        information_corporate_linear = findViewById(R.id.information_corporate_linear);
        information_corporate_address_linear = findViewById(R.id.information_corporate_address_linear);
        information_corporate_phone_linear = findViewById(R.id.information_corporate_phone_linear);
        loan_personal_linear = findViewById(R.id.loan_personal_linear);
        other_contacts_linear = findViewById(R.id.other_contacts_linear);

        information_mailbox_editText = findViewById(R.id.information_mailbox_editText);
        information_spouse_editText = findViewById(R.id.information_spouse_editText);
        information_live_editText = findViewById(R.id.information_live_editText);
        information_corporate_editText = findViewById(R.id.information_corporate_editText);
        information_corporate_address_editText = findViewById(R.id.information_corporate_address_editText);
        information_corporate_phone_editText = findViewById(R.id.information_corporate_phone_editText);

        information_mode_text = findViewById(R.id.information_mode_text);
        other_relatives_name = findViewById(R.id.other_relatives_name);
        other_relatives_phone = findViewById(R.id.other_relatives_phone);
        other_contacts_name = findViewById(R.id.other_contacts_name);
        other_contacts_phone = findViewById(R.id.other_contacts_phone);

        information_please_button = findViewById(R.id.information_please_button);
        other_relatives_wathet = findViewById(R.id.other_relatives_wathet);
        other_contacts_wathet = findViewById(R.id.other_contacts_wathet);

        if (!TextUtils.isEmpty(other)) {
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
        } else {
            information_mailbox_linear.setVisibility(View.VISIBLE);
            information_spouse_linear.setVisibility(View.VISIBLE);
            information_live_linear.setVisibility(View.VISIBLE);
            information_mode_linear.setVisibility(View.VISIBLE);
            information_corporate_linear.setVisibility(View.VISIBLE);
            information_corporate_address_linear.setVisibility(View.VISIBLE);
            information_corporate_phone_linear.setVisibility(View.VISIBLE);
            loan_personal_linear.setVisibility(View.VISIBLE);
            other_contacts_linear.setVisibility(View.VISIBLE);
        }

        information_mode_linear.setOnClickListener(this);
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", Long.parseLong(SharedPreferencesUtils.get(this, "uid", "").toString()));
        map.put("pid", Long.parseLong(pid));
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
        OkHttpManager.postAsync(HttpURL.getInstance().OTHER_INFO, "other", map, this);
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
        intent = new Intent();
        intent.putExtra("operator", "1");
        setResult(RESULT_CANCELED, intent);
        finish();
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    information_mode_text.setText(msg.obj.toString());
                    break;
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


    /**
     * 住房
     *
     * @return
     */
    private List<Map<String, Object>> getmode() {
        List<Map<String, Object>> list_car_life = new ArrayList<>();
        for (String aPurpose : mode_Strings) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", aPurpose);
            map.put("boolean", "1");
            list_car_life.add(map);
        }
        return list_car_life;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getContactID(OtherInformationActivity.this, mHandler, id);
            } else {
                Toast.makeText(this, "请授予联系人权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            intent.putExtra("operator", "2");
            setResult(RESULT_CANCELED, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
