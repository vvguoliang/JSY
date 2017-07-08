package com.jsy.jsydemo.view.Base1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.SpeedLoanDetailsListData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LoanDetailsActivity;
import com.jsy.jsydemo.view.BaseViewHolder;

/**
 * Created by vvguoliang on 2017/7/8.
 * <p>
 * 数据展示
 */

public class SpeedLoanDetailsListHolder extends BaseViewHolder<SpeedLoanDetailsListData> {

    private ImageView speed_loan_detailsList_image;

    private TextView speed_loan_detailsList_name;

    private TextView speed_loan_detailsList_text;

    private TextView speed_loan_detailsList_rate;

    private Context context;


    public SpeedLoanDetailsListHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.act_speedloan_details_list);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(SpeedLoanDetailsListData data) {
        super.setData(data);
        if (data != null) {
            Glide.with(context)
                    .load(data.getImg())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            speed_loan_detailsList_image.setImageResource(R.mipmap.ic_launcher);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    })
                    .into(speed_loan_detailsList_image);
            speed_loan_detailsList_name.setText(data.getPro_name());
            speed_loan_detailsList_text.setText("申请人数:" + data.getPro_describe());
            SpannableStringBuilder builder = new SpannableStringBuilder(speed_loan_detailsList_text.getText().toString());
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, 5, speed_loan_detailsList_text.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            speed_loan_detailsList_text.setText(builder);
            speed_loan_detailsList_rate.setText(data.getFeilv());
        }
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        speed_loan_detailsList_image = findViewById(R.id.speed_loan_detailsList_image);
        speed_loan_detailsList_name = findViewById(R.id.speed_loan_detailsList_name);
        speed_loan_detailsList_text = findViewById(R.id.speed_loan_detailsList_text);
        speed_loan_detailsList_rate = findViewById(R.id.speed_loan_detailsList_rate);
    }

    @Override
    public void onItemViewClick(SpeedLoanDetailsListData data) {
        super.onItemViewClick(data);
        Intent intent = new Intent(context, LoanDetailsActivity.class);
        intent.putExtra("id", data.getId());
        context.startActivity(intent);
    }
}
