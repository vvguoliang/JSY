package com.jsy.jsydemo.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vvguoliang on 2017/7/3.
 * <p>
 * 内部浏览器显示页面
 */

public class LoanWebViewActivity extends BaseActivity implements View.OnClickListener {

    private WebView webview;

    private String url = "";

    private ProgressBar banner_progressBar;

    private TextView title_view;

    private LinearLayout banner_linear;

    private RelativeLayout fail_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            LinearLayout tab_activity_lin = (LinearLayout) findViewById(R.id.tab_activity_lin);
            stateBarTint("#305591", true);
            statusFragmentBarDarkMode();
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tab_activity_lin.getLayoutParams();
            lp.height = DisplayUtils.px2dip(this, 48 * 13);
        }
        initView();
    }

    @Override
    protected void findViewById() {
        url = getIntent().getExtras().getString("url");
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        title_view = (TextView) findViewById(R.id.title_view);
        webview = (WebView) findViewById(R.id.banner_webview);

        banner_progressBar = (ProgressBar) findViewById(R.id.banner_progressBar);

        banner_linear = (LinearLayout) findViewById(R.id.banner_linear);
        fail_linear = (RelativeLayout) findViewById(R.id.fail_linear);

        getSettings();

    }

    @Override
    protected void initView() {
        webview.setWebViewClient(webViewClient);
        webview.setWebChromeClient(webChromeClient);
        banner_linear.setVisibility(View.VISIBLE);
        fail_linear.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void getSettings() {
        webview.loadUrl(url);
        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //渲染加速器
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH); //提高渲染的优先级
        webview.removeJavascriptInterface("searchBoxJavaBridge_"); //防止360
        WebSettings settings = webview.getSettings();

        settings.setBlockNetworkImage(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSaveFormData(false);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //LOAD_NO_CACHE设置,缓存模式LOAD_DEFAULT

        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setDatabaseEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.getSettings().setBlockNetworkImage(false);
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (view.getTitle().contains("404") || view.getTitle().contains("找不到")) {
                banner_progressBar.setVisibility(View.GONE);
                webview.setVisibility(View.GONE);
                title_view.setText("");
                banner_linear.setVisibility(View.GONE);
                fail_linear.setVisibility(View.VISIBLE);
            } else {
                title_view.setText(view.getTitle());
            }
        }
    };


    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            banner_progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                banner_progressBar.setVisibility(View.GONE);
            } else {
                banner_progressBar.setVisibility(View.VISIBLE);
            }
        }
    };

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
}
