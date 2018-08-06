package com.jsy.jsydemo.adapter;


import android.content.Context;
import android.view.ViewGroup;

import com.jsy.jsydemo.EntityClass.EvaluateData;
import com.jsy.jsydemo.view.Base1.EvaluateDataHolder;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.view.RecyclerAdapter;

/**
 * Created by jsy_zj on 2017/10/19.
 */

public class EvaluateRecyclerAdapter extends RecyclerAdapter<EvaluateData> {
    public EvaluateRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<EvaluateData> onCreateBaseViewHolder(Context context, ViewGroup parent, int viewType) {
        return new EvaluateDataHolder(context,parent);
    }
}
