package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jsy.jsydemo.EntityClass.LoanRecordBand;
import com.jsy.jsydemo.view.Base1.LoanRecordHolder;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.view.RecyclerAdapter;


/**
 * Created by vvguoliang on 2017/9/1.
 */

public class LoanRecordAdapter extends RecyclerAdapter<LoanRecordBand> {

    public LoanRecordAdapter(Context context) {
        super( context );
    }

    @Override
    public BaseViewHolder<LoanRecordBand> onCreateBaseViewHolder(Context context, ViewGroup parent, int viewType) {
        return new LoanRecordHolder( context, parent );
    }
}
