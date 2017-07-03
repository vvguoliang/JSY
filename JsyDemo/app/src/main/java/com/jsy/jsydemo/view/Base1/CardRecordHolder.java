package com.jsy.jsydemo.view.Base1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.HomeProduct;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LoanWebViewActivity;
import com.jsy.jsydemo.view.BaseViewHolder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CardRecordHolder extends BaseViewHolder<HomeProduct> {

    private TextView home_loan_product_title;
    private TextView home_loan_product_quota;
    private TextView home_loan_product_number;
    private TextView home_loan_product_interest_rate;

    private ImageView home_loan_product_image;

    private ViewGroup parent;

    private Context context;

    public CardRecordHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.holder_consume);
        this.parent = parent;
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(final HomeProduct object) {
        super.setData(object);
        if (object != null) {
            Glide.with(context)
                    .load(object.getImg())
                    .error(R.mipmap.ic_launcher)
                    .into(home_loan_product_image);
            home_loan_product_title.setText(object.getPro_name());
            home_loan_product_quota.setText(object.getPro_describe());
            home_loan_product_interest_rate.setText(parent.getContext().getString(R.string.name_loan_product_interest_rat) + object.getFeilv());


        }
    }

    RequestListener<String, GlideDrawable> listener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            Log.d("", "onException: " + e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
            home_loan_product_image.setImageResource(R.mipmap.ic_launcher);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache,
                                       boolean isFirstResource) {
            Log.e("", "isFromMemoryCache:" + isFromMemoryCache + "  model:" + model + " isFirstResource: " + isFirstResource);
            return false;
        }
    };

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        home_loan_product_image = findViewById(R.id.home_loan_product_image);
        home_loan_product_title = findViewById(R.id.home_loan_product_title);
        home_loan_product_quota = findViewById(R.id.home_loan_product_quota);
        home_loan_product_number = findViewById(R.id.home_loan_product_number);
        home_loan_product_interest_rate = findViewById(R.id.home_loan_product_interest_rate);
    }

    @Override
    public void onItemViewClick(HomeProduct object) {
        super.onItemViewClick(object);
        //点击事件
        Intent intent = new Intent(parent.getContext(), LoanWebViewActivity.class);
        intent.putExtra("url", object.getPro_link());
        parent.getContext().startActivity(intent);
    }
}