package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jsy.jsydemo.EntityClass.HomeProduct;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.view.RecyclerAdapter;

/**
 * Created by vvguoliang on 2017/7/5.
 */

public class SpeedLoanAdapter extends RecyclerAdapter<HomeProduct> {

    public SpeedLoanAdapter(Context context) {
        super(context);
    }

    public SpeedLoanAdapter(Context context, HomeProduct[] data) {
        super(context, data);
    }

    @Override
    public BaseViewHolder<HomeProduct> onCreateBaseViewHolder(Context context, ViewGroup parent, int viewType) {
        return null;
    }
}
