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

import com.jsy.jsydemo.EntityClass.SpeedLoanDataList;
import com.jsy.jsydemo.EntityClass.SpeedLoanDetailsListData;
import com.jsy.jsydemo.EntityClass.SpeedLoanDetailsListDataList;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.SpeedLoanDetailsListAdapter;
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
 * Created by vvguoliang on 2017/7/7.
 * 极速贷款详情列表
 */

public class SpeedLoanDetailsListActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private RefreshRecyclerView mRecyclerView;

    private Handler mHandler;

    private int page = 1;

    private SpeedLoanDetailsListAdapter mAdapter;

    private Long type;

    private SpeedLoanDetailsListData[] speedLoanDetailsListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fra_loan);
        type = getIntent().getExtras().getLong("type");
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.setStateBar(this, Color.parseColor("#305591"));
        }
        findViewById();
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
    protected void findViewById() {
        TextView title_view = (TextView) findViewById(R.id.title_view);
        if (type == 0) {
            title_view.setText(this.getString(R.string.name_Loan_recommend));
            getHttpIndex();
        } else {
            title_view.setText(this.getString(R.string.name_loan_speed_loan_x));
            getHttp();
        }
        findViewById(R.id.title_image).setOnClickListener(this);
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        mHandler = new Handler();
        mAdapter = new SpeedLoanDetailsListAdapter(this);

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
        mRecyclerView.setBackgroundResource(R.color.common_light_grey);
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

    public void getData(final boolean isRefresh) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    page = 1;
                    mAdapter.clear();
                    mAdapter.addAll(speedLoanDetailsListData);
                    mRecyclerView.dismissSwipeRefresh();
                    mRecyclerView.getRecyclerView().scrollToPosition(0);
                } else {
                    page++;
                    getHttp();
                }
            }
        }, 1500);
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCT_FILTER, "product_filter", map, this);
    }

    private void getHttpIndex() {
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCTINDEX, "product_filter", null, this);
    }

    @Override
    protected void initView() {

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
            SpeedLoanDetailsListDataList loanDetailsListData = JsonData.getInstance().getJsonSpeedLoanDetailsList(result);
            speedLoanDetailsListData = new SpeedLoanDetailsListData[loanDetailsListData.getLoanDetailsListData().size()];
            for (int i = 0; loanDetailsListData.getLoanDetailsListData().size() > i; i++) {
                speedLoanDetailsListData[i] = new SpeedLoanDetailsListData(loanDetailsListData.getLoanDetailsListData().get(i).getId(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getPro_name(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getPro_describe(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getPro_link(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getPro_hits(),
                        HttpURL.getInstance().HTTP_URL_PATH + loanDetailsListData.getLoanDetailsListData().get(i).getImg(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getOrder(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getEdufanwei(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getFeilv(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getFv_unit(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getZuikuaifangkuan(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getQixianfanwei(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getQx_unit(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getType(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getData_id(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getOther_id(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getStatus(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getCreated_at(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getUpdated_at(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getTiaojian(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getApi_type(),
                        loanDetailsListData.getLoanDetailsListData().get(i).getTags());
            }
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
