package com.jsy.jsydemo.view.Base1;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.SpeedLoanData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.SpeedLoanDetailsListActivity;
import com.jsy.jsydemo.view.BaseViewHolder;

/**
 * Created by vvguoliang on 2017/7/7.
 * <p>
 * 适配器数据
 */

public class SpeedLoanHolder extends BaseViewHolder<SpeedLoanData> {

    private ImageView speed_loan_image;

    private TextView speed_loan_text;

    private TextView speed_loan_loan_text;

    private TextView speedloan_name;

    private Context context;

    public SpeedLoanHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.act_speedloan);
        this.context = context;
    }

    @Override
    public void setData(SpeedLoanData data) {
        super.setData(data);
        if (data != null) {
            Glide.with(context)
                    .load(data.getIcon())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            speed_loan_image.setImageResource(R.mipmap.ic_launcher);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    })
                    .into(speed_loan_image);
            speed_loan_text.setText(data.getProperty_name());
            speed_loan_loan_text.setText(data.getMoney());
            speedloan_name.setText(data.getDescription());
        }
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        speed_loan_image = findViewById(R.id.speed_loan_image);
        speed_loan_text = findViewById(R.id.speed_loan_text);
        speed_loan_loan_text = findViewById(R.id.speed_loan_loan_text);
        speedloan_name = findViewById(R.id.speedloan_name);
    }

    @Override
    public void onItemViewClick(SpeedLoanData data) {
        super.onItemViewClick(data);
        Intent intent = new Intent(context, SpeedLoanDetailsListActivity.class);
        intent.putExtra("type", Long.parseLong(data.getProperty_type()));
        context.startActivity(intent);
    }
}

