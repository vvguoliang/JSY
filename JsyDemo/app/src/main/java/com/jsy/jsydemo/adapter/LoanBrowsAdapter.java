package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jsy.jsydemo.EntityClass.LoanBrowsData;
import com.jsy.jsydemo.EntityClass.LoanRecordBand;
import com.jsy.jsydemo.view.Base1.LoanBrowsHolder;
import com.jsy.jsydemo.view.Base1.LoanRecordHolder;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.view.RecyclerAdapter;

/**
 * Created by jsy_zj on 2017/10/23.
 */


public class LoanBrowsAdapter extends RecyclerAdapter<LoanBrowsData> {

    public LoanBrowsAdapter(Context context) {
        super( context );
    }

    @Override
    public BaseViewHolder<LoanBrowsData> onCreateBaseViewHolder(Context context, ViewGroup parent, int viewType) {
        return new LoanBrowsHolder( context, parent );
    }
}
