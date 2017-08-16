package com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.Share.ShareWxapTencent;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CameraUtils.UserCenterRealize;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/28.
 * 邀请好友
 */

public class FriendsActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private ImageView friends_image;

    private String share_ur = "";

    private String share_title = "";

    private String share_content = "";


    private Bitmap bitmap;

    private UserCenterRealize userCenterRealize = new UserCenterRealize();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_friends);
        userCenterRealize.getReadWRite(this, mHandler);
        getHttp();
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.friends_button:
                new ShareWxapTencent(this, share_ur, share_title, share_content, share_title, share_content, bitmap);
                break;
        }
    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_inviting_friends));

        friends_image = findViewById(R.id.friends_image);
        findViewById(R.id.friends_button).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    private void getHttp() {
        OkHttpManager.postAsync(HttpURL.getInstance().SHARE, "share", null, this);
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
        ToatUtils.showShort1(this, this.getString(R.string.network_timed));
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        if (name.equals("share")) {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = new JSONObject(jsonObject.optString("data"));
            share_title = object.optString("share_title");
            share_content = object.optString("share_content");
            String bg_img = object.optString("bg_img");
            share_ur = object.optString("share_url");

            Glide.with(this)
                    .load(HttpURL.getInstance().HTTP_URL_PATH + bg_img)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            friends_image.setImageResource(R.mipmap.ic_path_in_load);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    })
                    .into(friends_image);
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            bitmap = (Bitmap) msg.obj;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtil.getInstance().MY_PERMISSIONS_PHONE_READWRITE) {
            if (!TextUtils.isEmpty( grantResults[0] + "" ) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userCenterRealize.getReadWRite(this, mHandler);
            } else {
                Toast.makeText(this, "请授予SD卡权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
