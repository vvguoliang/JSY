package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CameraUtils.UserCenterRealize;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.PublicClass.CommissioningTimerUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.CircleIndicator;
import com.jsy.jsydemo.view.PublicPhoneDialog;
import com.jsy.jsydemo.webview.LoanWebViewActivity;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/23.
 * <p>
 * 启动页面
 */

@SuppressWarnings("deprecation")
public class CommissioningActivity extends FragmentActivity implements View.OnClickListener, DataCallBack {

    private ImageView commissioning_image;

    private ImageView commissioning_image1;

    private Button commissioning_button;

    private UserCenterRealize userCenterRealize = new UserCenterRealize();
    private String getImei = "";

    private CommissioningTimerUtils commissioningTimerUtils;

    private String boot_url = "";

    private ViewPager viewpager;
    private RelativeLayout viewpager_relat;
    private CircleIndicator indicator;
    private List<ImageView> viewList;

    private RelativeLayout commissioning_relat;

    private Button commissioning_loan_button;

    private String update_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_commissioning);
        findViewById();
        initView();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getWhite(this);
        }
    }

    protected void findViewById() {
        commissioning_image = findViewById(R.id.commissioning_image);
        commissioning_image1 = findViewById(R.id.commissioning_image1);
        commissioning_button = findViewById(R.id.commissioning_button);
        viewpager = findViewById(R.id.viewpager);
        viewpager_relat = findViewById(R.id.viewpager_relat);
        indicator = findViewById(R.id.indicator);

        commissioning_relat = findViewById(R.id.commissioning_relat);

        commissioning_loan_button = findViewById(R.id.commissioning_loan_button);

    }

    protected void initView() {
        if (TextUtils.isEmpty(SharedPreferencesUtils.get(this, "first_time", "").toString())) {
            commissioning_relat.setVisibility(View.GONE);
            viewpager_relat.setVisibility(View.VISIBLE);
        } else {
            commissioning_relat.setVisibility(View.VISIBLE);
            viewpager_relat.setVisibility(View.GONE);
            getUPDATE();
        }
        commissioning_button.setOnClickListener(this);
        commissioning_image1.setOnClickListener(this);
        initData();
        viewpager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewpager);
        commissioning_loan_button.setOnClickListener(this);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    if (ImmersiveUtils.BuildVERSION()) {
                        ImmersiveUtils.getInstance().getBlack(CommissioningActivity.this);
                    } else {
                        ImmersiveUtils.getInstance().getWhite(CommissioningActivity.this);
                    }
                    commissioning_loan_button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        viewList = new ArrayList<>();
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_boot_page1);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(imageView);
        imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_boot_page2);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(imageView);
        imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_boot_page3);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewList.add(imageView);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commissioning_button:
                commissioningTimerUtils.onFinish();
                finish();
                break;
            case R.id.commissioning_image1:
                if (!TextUtils.isEmpty(boot_url)) {
                    Intent intent = new Intent(this, LoanWebViewActivity.class);
                    intent.putExtra("url", boot_url);
                    startActivity(intent);
                }
                break;
            case R.id.commissioning_loan_button:
                SharedPreferencesUtils.put(this, "first_time", "1");
                getUPDATE();
                break;
        }
    }

    private void getHTTPActivity() {
        Map<String, Object> map = new HashMap<>();
        map.put("imei", getImei);
        map.put("mac", AppUtil.getInstance().getMacAddress());
        map.put("channel", AppUtil.getInstance().getChannel(this, 2));
        OkHttpManager.postAsync(HttpURL.getInstance().ACTIVITY, "commissioning_activity", map, this);
    }

    private void getBOOTAPP() {
        OkHttpManager.postAsync(HttpURL.getInstance().BOOTAPP, "boot_app", null, this);
    }

    private void getUPDATE() {
        Map<String, Object> map = new HashMap<>();
        map.put("version", AppUtil.getInstance().getVersionName(1, this));
        OkHttpManager.postAsync(HttpURL.getInstance().UPDATE, "update", map, this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        switch (name) {
            case "commissioning_activity":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
            case "boot_app":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
            case "update":
                ToatUtils.showShort1(this, this.getString(R.string.network_timed));
                break;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "commissioning_activity":
                getBOOTAPP();
                break;
            case "boot_app":
                JSONObject object = new JSONObject(result);
                if (object.optString("code").equals("0000")) {
                    commissioning_button.setVisibility(View.VISIBLE);
                    commissioning_image1.setVisibility(View.VISIBLE);
                    commissioning_image.setVisibility(View.GONE);
                    object = new JSONObject(object.optString("data"));
                    boot_url = object.optString("boot_url");
                    Glide.with(this)
                            .load(HttpURL.getInstance().HTTP_URL_PATH + object.optString("boot_img"))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                                    commissioning_button.setVisibility(View.GONE);
                                    commissioning_image1.setVisibility(View.GONE);
                                    commissioning_image.setVisibility(View.VISIBLE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource,
                                                               boolean b) {
                                    return false;
                                }
                            })
                            .into(commissioning_image1);
                    commissioningTimerUtils = new CommissioningTimerUtils(this, mHandler, commissioning_button,
                            (Long.parseLong(object.optString("time")) + 1) * 1000, 1000);
                    commissioningTimerUtils.start();
                } else {
                    commissioning_button.setVisibility(View.GONE);
                    commissioning_image1.setVisibility(View.GONE);
                    commissioning_image.setVisibility(View.VISIBLE);
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            case "update":
                object = new JSONObject(result);
                if (object.optString("code").equals("0001")) {
                    commissioning_relat.setVisibility(View.VISIBLE);
                    viewpager_relat.setVisibility(View.GONE);
                    commissioning_button.setVisibility(View.GONE);
                    userCenterRealize.getIMEIPHONE(this, mHandler, 100);
                } else if (object.optString("code").equals("0000")) {
                    object = new JSONObject(object.optString("data"));
                    update_url = object.optString("update_url");
                    String type = object.optString("type");
                    String content = object.optString("content");
                    getPhone(content, type);
                }
                break;
        }
    }

    private void getPhone(String msg, final String phone) {
        PublicPhoneDialog.Builder builder = new PublicPhoneDialog.Builder(this);
        builder.setTiltleMsg(msg);
        if (phone.equals("2")) {
            builder.setTitle("建议更新");
            builder.setContentViewCancel("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getBOOTAPP();
                    dialog.dismiss();
                }
            });
        } else {
            builder.setTitle("强制更新");
        }
        builder.setContentViewDetermine("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userCenterRealize.getUpdata(CommissioningActivity.this, update_url);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_PHONE_IMEI) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getIMEIPHONE(this, mHandler, 100);
            } else {
                ToatUtils.showShort1(this, "请授予手机权限");
            }
        } else if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_REQUEST_WRITE_SK) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getUpdata(this, update_url);
            } else {
                ToatUtils.showShort1(this, "请授予SD卡权限");
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
                    String[] phonestrings = msg.obj.toString().split("#");
                    if (TextUtils.isEmpty(SharedPreferencesUtils.get(CommissioningActivity.this, "username", "").
                            toString())) {
                        if (phonestrings.length > 2) {
                            getImei = phonestrings[1] + "," + phonestrings[2];
                        } else {
                            getImei = phonestrings[1];
                        }
                    } else if (phonestrings[0].equals(SharedPreferencesUtils.get(CommissioningActivity.this,
                            "username", "").toString())) {
                        getImei = phonestrings[1];
                    } else {
                        getImei = phonestrings[1];
                    }
                    getHTTPActivity();
                    break;
                case 101:
                    startActivity(new Intent(CommissioningActivity.this, MainActivity.class));
                    finish();
                    break;
            }
        }
    };

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "title";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    };
}
