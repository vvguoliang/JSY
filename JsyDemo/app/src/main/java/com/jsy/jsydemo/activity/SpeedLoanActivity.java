package com.jsy.jsydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.CardRecordAdapter;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.interfaces.Action;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.view.RefreshRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/7/5.
 * 极速贷
 */

public class SpeedLoanActivity extends BaseActivity implements View.OnClickListener, DataCallBack {


    private RefreshRecyclerView mRecyclerView;

    private CardRecordAdapter mAdapter;

    private Handler mHandler;

    private int page = 1;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fra_loan);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        if (name.equals("product_filter")) {
            Log.e("", "==" + request + "====" + e);
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        if (name.equals("product_filter")) {
            Log.e("", "==" + result);
        }
    }

    @Override
    protected void findViewById() {
        getHttp();
        mHandler = new Handler();
        mAdapter = new CardRecordAdapter(this);

        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.app_name));

        //添加footer
        final TextView footer = new TextView(this);
        footer.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dip2px(this, 24)));
        footer.setTextSize(16);
        footer.setGravity(Gravity.CENTER);
        footer.setText("");
        mAdapter.setFooter(footer);

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.loan_recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845, 0xFFE44F98, 0xFF2FAC21);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });

        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
                page++;
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });

    }

    @Override
    protected void initView() {

    }

    public void getData(final boolean isRefresh) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    page = 1;
                    mAdapter.clear();
//                    mAdapter.addAll(VirtualData);
                    mRecyclerView.dismissSwipeRefresh();
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                }
            }
        }, 1500);
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("os", "android");
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCT_FILTER, "product_filter", map, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loan_speed_linear:

                break;
            case R.id.loan_speed1_linear:
                break;
            case R.id.loan_speed2_linear:
                intent = new Intent(this, LoanWebViewActivity.class);
                intent.putExtra("url", "http://www.kuaicha.info/mobile/credit/credit.html");
                startActivity(intent);
                break;
            case R.id.loan_tab_linear:
                page++;
                getHttp();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LoanFragment"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LoanFragment");
    }
}
