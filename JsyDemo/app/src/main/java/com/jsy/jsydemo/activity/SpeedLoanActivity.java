package com.jsy.jsydemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.SpeedLoanData;
import com.jsy.jsydemo.EntityClass.SpeedLoanDataList;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.SpeedLoanAdapter;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.interfaces.Action;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.ToatUtils;
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

    private SpeedLoanAdapter mAdapter;

    private Handler mHandler;

    private int page = 1;

    private SpeedLoanData[] speedLoanData;

    private SpeedLoanDataList speedLoanDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fra_loan);
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.setStateBar(this, Color.parseColor("#305591"));
            ImmersiveUtils.stateBarTint(this, "#305591", true, false);
            //清除状态栏黑色字体
            statusFragmentBarDarkMode();
        }
        initView();
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        if (name.equals("product_filter")) {
            ToatUtils.showShort1(this, this.getString(R.string.network_timed));
        }
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        if (name.equals("product_filter")) {
            speedLoanDataList = JsonData.getInstance().getJsonSpeedLoanData(result);
            speedLoanData = new SpeedLoanData[speedLoanDataList.getLoanDataList().size()];
            for (int i = 0; speedLoanDataList.getLoanDataList().size() > i; i++) {
                speedLoanData[i] = new SpeedLoanData(speedLoanDataList.getLoanDataList().get(i).getProperty_id(),
                        speedLoanDataList.getLoanDataList().get(i).getProperty_type(),
                        speedLoanDataList.getLoanDataList().get(i).getProperty_name(),
                        speedLoanDataList.getLoanDataList().get(i).getMoney(),
                        HttpURL.getInstance().HTTP_URL_PATH + speedLoanDataList.getLoanDataList().get(i).getIcon(),
                        speedLoanDataList.getLoanDataList().get(i).getDescription());
            }
        }
    }

    @Override
    protected void findViewById() {
        getHttp();
        mHandler = new Handler();
        mAdapter = new SpeedLoanAdapter(this);

        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);
        TextView title_view = (TextView) findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_sky_loan));

        mAdapter.removeHeader();
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
        mRecyclerView.setBackgroundResource(R.color.common_light_grey);
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
                    mAdapter.addAll(speedLoanData);
                    mRecyclerView.dismissSwipeRefresh();
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                } else {
                    page++;
                    getHttp();
                }
            }
        }, 1000);
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("os", "android");
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCTTYPE, "product_filter", map, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
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
