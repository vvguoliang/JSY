package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jsy.jsydemo.EntityClass.SpeedLoanData;
import com.jsy.jsydemo.view.Base1.SpeedLoanHolder;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.view.RecyclerAdapter;

/**
 * Created by vvguoliang on 2017/7/5.
 * 适配器
 */

public class SpeedLoanAdapter extends RecyclerAdapter<SpeedLoanData> {

    public SpeedLoanAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<SpeedLoanData> onCreateBaseViewHolder(Context context, ViewGroup parent, int viewType) {
        return new SpeedLoanHolder(context, parent);
    }
}
