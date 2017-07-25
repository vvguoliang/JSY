package com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.Share.ShareWxapTencent;
import com.jsy.jsydemo.activity.LogoActivity;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/6/28.
 * 邀请好友
 */

public class FriendsActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private ImageView friends_image;
    private Button friends_button;
    private ShareWxapTencent shareWxapTencent;

    private String mQqTitle = "[沙小僧理财]送你600元启动资金,跟小沙一起取金吧!";

    private String mQqSummary = "预期15%年化收益，会理财更赚钱！沙小僧，靠谱儿！";

    private String mWxwebtitle = "【沙小僧理财】送你600元启动资金,跟小沙一起取金吧!";

    private String mWxwebdescription = "我在沙小僧理财，安全可靠福利又多，新手注册即送600元，最高15%年化收益，快来一起赚~";

    private String url = "";

    private  String share_title = "";

    private String share_content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_friends);
        getHttp();
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            LinearLayout tab_activity_lin = (LinearLayout) findViewById(R.id.tab_activity_lin);
            stateBarTint("#305591", true);
            statusFragmentBarDarkMode();
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_activity_lin.getLayoutParams();
            lp.height = DisplayUtils.px2dip(this, 48 * 11);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.friends_button:
                shareWxapTencent = new ShareWxapTencent(this, url, share_title, share_content, share_title, share_content);
                break;

        }

    }

    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_personal_inviting_friends));

        friends_image = (ImageView) findViewById(R.id.friends_image);
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

    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        if(name.equals("share")){
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = new JSONObject(jsonObject.optString("data"));
            String id = object.optString("id");
            share_title = object.optString("share_title");
            share_content = object.optString("share_content");
            String bg_img = object.optString("bg_img");
            url = object.optString("share_ur");

            Glide.with(this)
                    .load(HttpURL.getInstance().HTTP_URL_PATH + bg_img)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            friends_image.setImageResource(R.mipmap.ic_launcher);
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
}
