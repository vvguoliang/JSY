package com.jsy.jsydemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jsy.jsydemo.EntityClass.HomeProduct;
import com.jsy.jsydemo.view.Base1.CardRecordHolder;
import com.jsy.jsydemo.view.BaseViewHolder;
import com.jsy.jsydemo.view.RecyclerAdapter;

public class CardRecordAdapter extends RecyclerAdapter<HomeProduct> {

    public CardRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<HomeProduct> onCreateBaseViewHolder(Context context, ViewGroup parent, int viewType) {
        return new CardRecordHolder(context,parent);
    }

}