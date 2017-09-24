package com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.LoanRecordBand;
import com.jsy.jsydemo.EntityClass.LoanRecordBandList;
import com.jsy.jsydemo.EntityClass.SpeedLoanData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.adapter.LoanRecordAdapter;
import com.jsy.jsydemo.adapter.SpeedLoanAdapter;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.interfaces.Action;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.RefreshRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/9/1.
 * <p>
 * 借款记录
 */

public class LoanRecordAcitivty extends BaseActivity implements DataCallBack, View.OnClickListener {

    private RefreshRecyclerView mRecyclerView;

    private int page = 1;

    private Handler mHandler;

    private LoanRecordAdapter mAdapter;

    private LoanRecordBand[] loanRecordBand;

    private LoanRecordBandList loanRecordBandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.fra_loan );
        findViewById();
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B( this );
        }
        initView();
    }

    @Override
    protected void findViewById() {
        mHandler = new Handler();
        mAdapter = new LoanRecordAdapter( this );

        findViewById( R.id.title_image ).setVisibility( View.VISIBLE );
        findViewById( R.id.title_image ).setOnClickListener( this );
        TextView title_view = findViewById( R.id.title_view );
        title_view.setText( this.getString( R.string.name_loan_personal_Loan_record ) );

        mAdapter.removeHeader();
        //添加footer
        final TextView footer = new TextView( this );
        footer.setLayoutParams( new LinearLayoutCompat.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dip2px( this, 24 ) ) );
        footer.setTextSize( 16 );
        footer.setGravity( Gravity.CENTER );
        footer.setText( "" );
        mAdapter.setFooter( footer );

        mRecyclerView = findViewById( R.id.loan_recycler_view );
        mRecyclerView.setSwipeRefreshColors( 0xFF437845, 0xFFE44F98, 0xFF2FAC21 );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        mRecyclerView.setAdapter( mAdapter );
        mRecyclerView.setBackgroundResource( R.color.common_light_grey );
        mRecyclerView.setRefreshAction( new Action() {
            @Override
            public void onAction() {
                getData( true );
            }
        } );

        mRecyclerView.setLoadMoreAction( new Action() {
            @Override
            public void onAction() {
                getData( false );
                page++;
            }
        } );

        mRecyclerView.post( new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData( true );
            }
        } );

    }

    public void getData(final boolean isRefresh) {
        mHandler.postDelayed( new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    page = 1;
                    mAdapter.clear();
                    mAdapter.addAll( loanRecordBand );
                    mRecyclerView.dismissSwipeRefresh();
                    mRecyclerView.getRecyclerView().scrollToPosition( 0 );
                } else {
                    page++;
                    getHttp();
                }
            }
        }, 1000 );
    }

    @Override
    protected void initView() {
        getHttp();
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put( "uid", Long.parseLong( SharedPreferencesUtils.get( this, "uid", "" ).toString() ) );
        OkHttpManager.postAsync( HttpURL.getInstance().USERINFORECORD, "record", map, this );
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume( this );
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause( this );
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        ToatUtils.showShort1( this, this.getString( R.string.network_timed ) );
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        loanRecordBandList = JsonData.getInstance().getJsonLoanRexord( result );
        if (loanRecordBandList != null && loanRecordBandList.getLoanRecordBands() != null
                && loanRecordBandList.getLoanRecordBands().size() > 0) {
            loanRecordBand = new LoanRecordBand[loanRecordBandList.getLoanRecordBands().size()];
            for (int i = 0; loanRecordBandList.getLoanRecordBands().size() > i; i++) {
//                loanRecordBand[i] = new LoanRecordBand( loanRecordBandList.getLoanRecordBands().get( i ).getPro_name(),
//                        HttpURL.getInstance().HTTP_URL_PATH + loanRecordBandList.getLoanRecordBands().get( i ).getImg(),
//                        loanRecordBandList.getLoanRecordBands().get( i ).getCreated_at() );
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_image:
                finish();
                break;
        }
    }
}
