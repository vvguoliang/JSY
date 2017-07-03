package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jsy.jsydemo.EntityClass.QuickBank;
import com.jsy.jsydemo.view.Base1.QuickBankHolder;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.view.RecyclerAdapter;

/**
 * Created by vvguoliang on 2017/7/3.
 * 快速办卡
 */

public class QuickCardAdapter extends RecyclerAdapter<QuickBank> {

    public QuickCardAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<QuickBank> onCreateBaseViewHolder(Context context, ViewGroup parent, int viewType) {
        return new QuickBankHolder(context, parent);
    }
}
