package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jsy.jsydemo.EntityClass.SpeedLoanDetailsListData;
import com.jsy.jsydemo.view.Base1.SpeedLoanDetailsListHolder;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.view.RecyclerAdapter;

/**
 * Created by vvguoliang on 2017/7/7.
 * 适配器
 */

public class SpeedLoanDetailsListAdapter extends RecyclerAdapter<SpeedLoanDetailsListData> {

    public SpeedLoanDetailsListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<SpeedLoanDetailsListData> onCreateBaseViewHolder(Context context, ViewGroup parent, int viewType) {
        return new SpeedLoanDetailsListHolder(context, parent);
    }
}
