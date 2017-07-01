package com.jsy.jsydemo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsy.jsydemo.R;
import com.jsy.jsydemo.utils.NetWorkUtils;
import com.jsy.jsydemo.utils.ToatUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能介绍
 *
 * @Title: BaseFragment.java
 * @Description: TODO Fragment 基类初始化 layout布局
 * @author: 秦国良
 * @data: 2016年5月16日 下午8:09:03
 * @ModifiedPerson:
 * @ModifiedPersonData：2016年5月16日下午8:09:03 @ModifyRemarks：
 * @version: V1.1.0
 * @Copyright 沙小僧
 */
public abstract class BaseFragment extends Fragment {//implements OnClickRefreshListener {

    private View mRootView;

    protected int onClickNum = 0;

    private long time = 0;

//    private NotWorkDialog notWorkDialog;

    private Activity mContext;

    public BaseFragment() {
        super();
    }


    public BaseFragment(Activity activity) {
        super();
        this.mContext = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null)

        {
            String FRAGMENTS_TAG = "Android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);

        }
        mRootView = inflater.inflate(getLayout(), container, false);
        initView();
        return mRootView;
    }

    public View getmRootView() {
        return mRootView;
    }

    /**
     * 初始化Fragment的Layout
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 通过ID获取相应的View
     *
     * @param id
     * @return
     */
    protected View findViewById(int id) {
        if (mRootView == null) {
            return null;
        }
        return mRootView.findViewById(id);
    }

    public void netWorkChange(int netWorkState) {
    }

    protected void showNotWorkDialog() {
//        if (notWorkDialog == null) {
//            notWorkDialog = new NotWorkDialog(getActivity());
//            notWorkDialog.setOnClickRefreshListener(this);
//        }
//        if (getUserVisibleHint()) {
//            if (!getActivity().isFinishing())
//                notWorkDialog.show();
//        }
    }

    protected void dissNotWorkDialog() {
//        if (notWorkDialog != null) {
//            notWorkDialog.dismiss();
//        }
    }

//    @Override
//    public void onRefresh() {
//
//    }

    /**
     * 判断是否是第一次看到这个页面 @Title: isFirstTime @author: xusonghui @Description:
     * TODO(这里用一句话描述这个方法的作用) @param: @return @return: boolean @throws
     */
    protected boolean isFirstTime() {
        return onClickNum <= 1;
    }

    /**
     * 在MainActivity里面会在点击的时候去调用该方法，用于在加载的时候加载数据，建议在这里面加载数据 @Title:
     * loadData @author: xusonghui @Description:
     * TODO(这里用一句话描述这个方法的作用) @param: @return: void @throws
     */
    public void loadData() {
        onClickNum++;
    }

    private void remind(String msg, boolean isExit) {
        waitDismiss();
//        Getintent.getInstance().getIntent(getActivity(), StartpageActivity.class, 0, new String[]{"sxsmian"}, "11");
//        if ("0".equals(msg))
//            ToatUtils.showLong(mContext, "距离上次登录时间过长,请重新登录");
//        else
//            ToatUtils.showLong(mContext, msg);
    }


//    private void exitRoutine(String flag, String title, String msg) {
//        if (StringUtil.isNullOrEmpty(msg))
//            msg = getString(R.string.sys_maintenance);
//        CustomSysSureDialog builder = new CustomSysSureDialog.Builder(mContext);
//        builder.setClose(flag, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                dialog.dismiss();
//                BaseActivityManager.getActivityManager().finishAllActivity();
//                System.exit(0);
//            }
//        });
//        if (!StringUtil.isNullOrEmpty(title)) {
//            builder.setTitle(title);
//        } else {
//            builder.setTitle(getString(R.string.sys_tips));
//        }
//        builder.setMessage(msg);
//        builder.setPositiveButton(getString(R.string.sys_myknow),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        BaseActivityManager.getActivityManager().finishAllActivity();
//                        System.exit(0);
//                    }
//                });
//        Dialog dialog = builder.create();
//        dialog.setCancelable(false);
//        dialog.show();
//    }

//    private SXSDialog mSxsDialog;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            loadData();
        } else {

        }
    }

//    private SXSProgressBar mSxsProgressBar;

    /**
     * 网络请求耗时操作，弹出等等框 @Title: loadDataWait @author: xusonghui @Description:
     * TODO(这里用一句话描述这个方法的作用) @param: @return: void @throws
     */
    protected void loadDataWait() {
//        if (mSxsProgressBar == null)
//            mSxsProgressBar = new SXSProgressBar(getContext());
//        if (getUserVisibleHint())
//            mSxsProgressBar.show();
    }

    /**
     * 隐藏等等框 @Title: waitDismiss @author: xusonghui @Description:
     * TODO(这里用一句话描述这个方法的作用) @param: @return: void @throws
     */
    protected void waitDismiss() {
//        if (mSxsProgressBar != null)
//            mSxsProgressBar.dismiss();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (System.currentTimeMillis() - time < 1000) {
            return;
        }
        time = System.currentTimeMillis();
        if (!NetWorkUtils.isNetworkConnected(getActivity())) {
            ToatUtils.showShort1(getActivity(), getString(R.string.no_network_timed));
            return;
        }
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        if (System.currentTimeMillis() - time < 1000) {
            return;
        }
        time = System.currentTimeMillis();
        if (!NetWorkUtils.isNetworkConnected(getActivity())) {
            ToatUtils.showShort1(getActivity(), getString(R.string.no_network_timed));
            return;
        }
        super.startActivity(intent, options);
    }
}
