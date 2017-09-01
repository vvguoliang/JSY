package com.jsy.jsydemo.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsy.jsydemo.EntityClass.HomeLoanBannerList;
import com.jsy.jsydemo.EntityClass.HomeProduct;
import com.jsy.jsydemo.EntityClass.HomeProductList;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LoanDetailsActivity;
import com.jsy.jsydemo.activity.LogoActivity;
import com.jsy.jsydemo.activity.SpeedLoanActivity;
import com.jsy.jsydemo.activity.SpeedLoanDetailsListActivity;
import com.jsy.jsydemo.adapter.LoanFragAdaperListview;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.adapter.BannerLoopAdapter;
import com.jsy.jsydemo.adapter.CardRecordAdapter;
import com.jsy.jsydemo.base.BaseFragment;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.interfaces.Action;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.DisplayUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.view.Marquee;
import com.jsy.jsydemo.view.MarqueeView;
import com.jsy.jsydemo.view.RefreshRecyclerView;
import com.jsy.jsydemo.webview.LoanWebViewActivity;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Request;


/**
 * Created by vvguoliang on 2017/6/23.
 * <p>
 * 借款
 */
@SuppressWarnings({"deprecation", "StatementWithEmptyBody", "LoopStatementThatDoesntLoop"})
@SuppressLint({"ValidFragment", "InflateParams", "ResourceType"})
public class LoanFragment extends BaseFragment implements DataCallBack, View.OnClickListener {

    private Activity mActivity;

    public LoanFragment() {
        super();
    }

    public LoanFragment(Activity activity) {
        super( activity );
        this.mActivity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        this.mActivity = (Activity) context;
    }

    private RefreshRecyclerView mRecyclerView;

    private CardRecordAdapter mAdapter;

    private Handler mHandler;

    private int page = 1;

    private LinearLayout loan_frame;

    private ViewPager loan_viewpage;

    //图片地址集合( 项目中一般是对于的HTTP地址 )
    List<Map<String, String>> mImageUrl = null;
    //banner中图片的集合
    List<ImageView> mBannerImageViews = new ArrayList<>();
    //banner上点点的集合
    List<ImageView> mBannerDots = new ArrayList<>();

    private HomeProductList homeProductList;

    private HomeProduct[] VirtualData;

    private Intent intent = null;

    private LoanFragAdaperListview loanFragAdaperListview;

    private GridView loan_frame_gridView;

    private List<Map<String, String>> mapList;
    private List<Marquee> marquees;
    private MarqueeView marqueeView;
    private String[] welcomeArrays;//成员变量

    @Override
    protected int getLayout() {
        return R.layout.fra_loan;
    }

    @Override
    protected void initView() {
        getHttp();
        OkHttpManager.postAsync( HttpURL.getInstance().BANNER, "banner", null, this );
        getHttpPRODUCTCATELIST();
        mHandler = new Handler();
        mAdapter = new CardRecordAdapter( mActivity );
        TextView title_view = (TextView) findViewById( R.id.title_view );
        title_view.setText( mActivity.getString( R.string.app_name ) );

        //添加Header
        View mHeader = LayoutInflater.from( mActivity ).inflate( R.layout.fra_loan_top, null );
        mHeader.setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
                AppUtil.getInstance().Dispay( mActivity )[1] / 2 + DisplayUtils.dip2px( mActivity, 100 ) ) );
        getHeader( mHeader );
        mAdapter.setHeader( mHeader );
        mAdapter.removeFooter();

        mRecyclerView = (RefreshRecyclerView) findViewById( R.id.loan_recycler_view );
        mRecyclerView.setSwipeRefreshColors( 0xFF437845, 0xFFE44F98, 0xFF2FAC21 );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( mActivity ) );
        mRecyclerView.setAdapter( mAdapter );
        mRecyclerView.setRefreshAction( new Action() {
            @Override
            public void onAction() {
                getData( true );
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
                page = 1;
                mAdapter.clear();
                mAdapter.addAll( VirtualData );
                mRecyclerView.dismissSwipeRefresh();
                mRecyclerView.getRecyclerView().scrollToPosition( 0 );
            }
        }, 1000 );
    }

    private void getHeader(View mHeader) {
        marqueeView = mHeader.findViewById( R.id.marqueeView );
//        mHeader.findViewById(R.id.loan_speed_linear).setOnClickListener(this);
//        ImageView loan_speed = mHeader.findViewById(R.id.loan_speed);
//        mHeader.findViewById(R.id.loan_speed1_linear).setOnClickListener(this);
//        ImageView loan_speed1 = mHeader.findViewById(R.id.loan_speed1);
//        mHeader.findViewById(R.id.loan_speed2_linear).setOnClickListener(this);
//        ImageView loan_speed2 = mHeader.findViewById(R.id.loan_speed2);
        loan_frame_gridView = mHeader.findViewById( R.id.loan_frame_gridView );
        loanFragAdaperListview = new LoanFragAdaperListview( mActivity );
        loan_frame_gridView.setAdapter( loanFragAdaperListview );

        mHeader.findViewById( R.id.loan_tab_linear ).setOnClickListener( this );

        loan_viewpage = mHeader.findViewById( R.id.loan_viewpage );
        final ViewGroup.LayoutParams lp = loan_viewpage.getLayoutParams();
        lp.height = DisplayUtils.dip2px( mActivity, 150 );
        loan_viewpage.setLayoutParams( lp );
        loan_viewpage.setOnPageChangeListener( new NavigationPageChangeListener() );
        loan_frame = mHeader.findViewById( R.id.loan_frame );

        loan_frame_gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mapList != null && mapList.size() > 0) {
                    if (!TextUtils.isEmpty( SharedPreferencesUtils.get( mActivity, "uid", "" ).toString() )) {
                        if (mapList.size() - 1 == i) {
                            intent = new Intent( mActivity, LoanWebViewActivity.class );
                            intent.putExtra( "url", HttpURL.getInstance().HTTP_URL_JUAICHA );
                            mActivity.startActivity( intent );
                        } else {
                            if (mapList.get( i ).get( "cat_name" ).equals( getString( R.string.name_Loan_recommend ) )) {
                                intent = new Intent( mActivity, SpeedLoanDetailsListActivity.class );
                                intent.putExtra( "type", 0 );
                                mActivity.startActivity( intent );
                            } else if (mapList.get( i ).get( "cat_name" ).equals( getString( R.string.name_sky_loan ) )) {
                                mActivity.startActivity( new Intent( mActivity, SpeedLoanActivity.class ) );
                            } else {
                                intent = new Intent( mActivity, LoanDetailsActivity.class );
                                intent.putExtra( "id", mapList.get( i ).get( "id" ) );
                                mActivity.startActivity( intent );
                            }
                        }
                    } else {
                        mActivity.startActivity( new Intent( mActivity, LogoActivity.class ) );
                    }
                }
            }
        } );
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put( "page", page );
        map.put( "os", "android" );
        OkHttpManager.postAsync( HttpURL.getInstance().HOMEPRODUCT, "home_product", map, this );
    }

    private void getHttpPRODUCTCATELIST() {
        OkHttpManager.postAsync( HttpURL.getInstance().PRODUCTCATELIST, "product", null, this );
    }

    // -------------------------------------------------------------------------
    // Banner相关
    // -------------------------------------------------------------------------
    private void refreshBanner() {

        mBannerImageViews.clear();
        mBannerDots.clear();
        loan_frame.removeAllViews();
        for (int i = 0; mImageUrl.size() > i; i++) {
            ImageView iv = new ImageView( mActivity );
            iv.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
            iv.setScaleType( ImageView.ScaleType.CENTER_CROP );
            mBannerImageViews.add( iv );
        }

        //循环添加 点
        for (int i = 0; i < mImageUrl.size(); i++) {
            ImageView view = new ImageView( mActivity );
            int w = DisplayUtils.dip2px( mActivity, 10 );
            int margin = DisplayUtils.dip2px( mActivity, 8 );
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( w, w );
            layoutParams.setMargins( margin, 0, 0, 0 );
            view.setLayoutParams( layoutParams );
            view.setImageResource( R.mipmap.dot_grey );
            if (i == getCurrentItem()) {
                view.setImageResource( R.mipmap.dot_white );
            }
            view.setScaleType( ImageView.ScaleType.MATRIX );
            mBannerDots.add( view );
            loan_frame.addView( view );
        }

        // 如果这样设置会一页一页的滑动过去 直接就ANR了!!!
        //banner.setCurrentItem(Integer.MAX_VALUE/2);

        //通过反射修改内部索引位置
        Class bannerClass = loan_viewpage.getClass();
        try {
            Field field = bannerClass.getDeclaredField( "mCurItem" );
            try {
                field.setAccessible( true );
                field.setInt( loan_viewpage, Integer.MAX_VALUE / 2 );
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        currentItem = Integer.MAX_VALUE / 2;
        loan_viewpage.setAdapter( new BannerLoopAdapter( mActivity, mBannerImageViews, mImageUrl ) );
        pollBanner();
    }

    private int getCurrentItem() {
        return currentItem % mBannerImageViews.size();
    }

    //-------------------------------------
    // banner 自动翻页的相关代码, 不需要删除即可
    //-------------------------------------
    private Timer bannerTimer;
    private TimerTask bannerTask;
    private int currentItem;

    private void pollBanner() {
        if (bannerTimer == null || bannerTask == null) {
            bannerTimer = new Timer();
            bannerTask = new TimerTask() {
                @Override
                public void run() {
                    currentItem = currentItem + 1;
                    bannerHandler.obtainMessage().sendToTarget();
                }

            };
            bannerTimer.schedule( bannerTask, AppUtil.getInstance().TIME );// 3秒钟自动翻页一次
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler bannerHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            loan_viewpage.setCurrentItem( currentItem );
        }
    };

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        ToatUtils.showShort1( mActivity, this.getString( R.string.network_timed ) );
    }

    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "banner":
                HomeLoanBannerList homeLoanBannerList = JsonData.getInstance().getJsonLaonHome( result );
                mImageUrl = new ArrayList<>();
                for (int i = 0; homeLoanBannerList.getLoanBanners().size() > i; i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put( "path", HttpURL.getInstance().HTTP_URL_PATH + homeLoanBannerList.getLoanBanners().get( i ).getImg().
                            replace( "\\", "" ) );
                    map.put( "url", homeLoanBannerList.getLoanBanners().get( i ).getImg_url() );
                    mImageUrl.add( map );
                }
                //设置ViewPage的页面内容
                refreshBanner();
                break;
            case "home_product":
                homeProductList = new HomeProductList();
                homeProductList = JsonData.getInstance().getJsonLoanProduct( result );
                if (homeProductList.getHomeProductList().size() == 0) {
                    page = 0;
                    mRecyclerView.showNoMore();
                } else {
                    VirtualData = new HomeProduct[homeProductList.getHomeProductList().size()];
                    for (int i = 0; homeProductList.getHomeProductList().size() > i; i++) {
                        VirtualData[i] = new HomeProduct( homeProductList.getHomeProductList().get( i ).getId(),
                                homeProductList.getHomeProductList().get( i ).getPro_name(),
                                homeProductList.getHomeProductList().get( i ).getPro_describe(),
                                homeProductList.getHomeProductList().get( i ).getPro_link(),
                                homeProductList.getHomeProductList().get( i ).getPro_hits(),
                                HttpURL.getInstance().HTTP_URL_PATH +
                                        homeProductList.getHomeProductList().get( i ).getImg().replace( "\\", "" ),
                                homeProductList.getHomeProductList().get( i ).getOrder(),
                                homeProductList.getHomeProductList().get( i ).getEdufanwei(),
                                homeProductList.getHomeProductList().get( i ).getFeilv(),
                                homeProductList.getHomeProductList().get( i ).getApi_type(),
                                homeProductList.getHomeProductList().get( i ).getZuikuaifangkuan(),
                                homeProductList.getHomeProductList().get( i ).getQixianfanwei(),
                                homeProductList.getHomeProductList().get( i ).getQx_unit(),
                                homeProductList.getHomeProductList().get( i ).getType(),
                                homeProductList.getHomeProductList().get( i ).getData_id(),
                                homeProductList.getHomeProductList().get( i ).getOther_id(),
                                homeProductList.getHomeProductList().get( i ).getStatus(),
                                homeProductList.getHomeProductList().get( i ).getCreated_at(),
                                homeProductList.getHomeProductList().get( i ).getUpdated_at(),
                                homeProductList.getHomeProductList().get( i ).getTiaojian(),
                                homeProductList.getHomeProductList().get( i ).getApi_type() );
                    }
                    mAdapter.clear();
                    mAdapter.addAll( VirtualData );
                    mAdapter.notifyDataSetChanged();
                }
                marquees = new ArrayList<>();
                for (int i = 0; homeProductList.getHomeProductList().size() > i; i++) {
                    Marquee marquee = new Marquee();
                    marquee.setImgUrl( HttpURL.getInstance().HTTP_URL_PATH +
                            homeProductList.getHomeProductList().get( i ).getImg().replace( "\\", "" ) );
                    if (homeProductList.getHomeProductList().get( i ).getPro_name().length() > 3) {
                        marquee.setTitle( homeProductList.getHomeProductList().get( i ).getPro_name().substring( 0, 3 ) );
                    } else {
                        marquee.setTitle( homeProductList.getHomeProductList().get( i ).getPro_name() );
                    }
                    marquee.setContext( getWellcometips() + "**  1" + phoneRandom1() + "****" + testRandom1() + " 今日借款" + Random1() + "元" + " 已到账" );
                    marquees.add( marquee );
                }
                marqueeView.setImage( true );
                marqueeView.startWithList( marquees );

                break;
            case "product":
                mapList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject( result );
                JSONArray array = new JSONArray( jsonObject.optString( "data" ) );
                for (int i = 0; array.length() > i; i++) {
                    JSONObject object = array.getJSONObject( i );
                    Map<String, String> map = new HashMap<>();
                    map.put( "id", object.optString( "id" ) );
                    map.put( "cat_name", object.optString( "cat_name" ) );
                    map.put( "cat_icon", HttpURL.getInstance().HTTP_URL_PATH + object.optString( "cat_icon" ) );
                    map.put( "list_order", object.optString( "list_order" ) );
                    mapList.add( map );
                }
                loanFragAdaperListview = new LoanFragAdaperListview( mActivity, mapList );
                loan_frame_gridView.setAdapter( loanFragAdaperListview );
                loanFragAdaperListview.notifyDataSetChanged();
                break;
        }

    }

    //生成随机数 手机后4为随机数
    private String testRandom1() {
        return (new Random()).nextInt( 9999 ) + "";
    }

    private String phoneRandom1() {
        return (new Random()).nextInt( 99 ) + "";
    }

    private String Random1() {
        return (new Random()).nextInt( 9999 ) + "";
    }

    // 获得欢迎语资源
    public String getWellcometips() {
        welcomeArrays = this.getResources().getStringArray( R.array.tips );
        int id = (int) (Math.random() * (welcomeArrays.length - 1));//随机产生一个index索引
        return welcomeArrays[id];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loan_tab_linear:
                page++;
                getHttp();
                break;
        }
    }

    class NavigationPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            position = position % mBannerImageViews.size();
            for (int i = 0; i < mBannerImageViews.size(); i++) {
                mBannerDots.get( i ).setImageResource( R.mipmap.dot_grey );
                if (position == i) {
                    mBannerDots.get( position ).setImageResource( R.mipmap.dot_white );
                }
            }
            //页面变动的时候重新设置定时器3秒钟后翻页  ( 防止手动滑动的时候定时器还在滑动 )
            if (bannerTimer != null && bannerTask != null) {
                bannerTask.cancel();
                bannerTask = new TimerTask() {
                    @Override
                    public void run() {
                        currentItem = currentItem + 1;
                        bannerHandler.obtainMessage().sendToTarget();
                    }
                };
                bannerTimer.schedule( bannerTask, AppUtil.getInstance().TIME );
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (bannerTimer != null) {
            bannerTimer.cancel();
            bannerTimer = null;
        }
        if (bannerTask != null) {
            bannerTask.cancel();
            bannerTask = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pollBanner();
        MobclickAgent.onPageStart( "LoanFragment" ); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd( "LoanFragment" );
    }
}
