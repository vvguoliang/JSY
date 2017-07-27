package com.jsy.jsydemo.view.Base1;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.QuickBank;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.LogoActivity;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.webview.LoanWebViewActivity;
import com.jsy.jsydemo.view.BaseViewHolder;

/**
 * Created by vvguoliang on 2017/7/3.
 * 快读办卡 类
 */

public class QuickBankHolder extends BaseViewHolder<QuickBank> {

    private ImageView quick_bank_image;

    private TextView quick_bank_name;

    private TextView quick_bank_name_text;

    private Context context;

    public QuickBankHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.holder_quick_bank);
        this.context = context;
    }

    @Override
    public void setData(QuickBank data) {
        super.setData(data);
        if (data != null) {
            Glide.with(context)
                    .load(data.getIcon())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            quick_bank_image.setImageResource(R.mipmap.ic_path_in_load);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    })
                    .into(quick_bank_image);
            quick_bank_name.setText(data.getName());
            quick_bank_name_text.setText(data.getDescribe());
        }
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        quick_bank_image = findViewById(R.id.quick_bank_image);
        quick_bank_name = findViewById(R.id.quick_bank_name);
        quick_bank_name_text = findViewById(R.id.quick_bank_name_text);
    }

    @Override
    public void onItemViewClick(QuickBank data) {
        super.onItemViewClick(data);
        if (TextUtils.isEmpty(SharedPreferencesUtils.get(context, "uid", "").toString())) {
            context.startActivity(new Intent(context, LogoActivity.class));
        } else {
            //点击事件
            Intent intent = new Intent(context, LoanWebViewActivity.class);
            intent.putExtra("url", data.getLink());
            context.startActivity(intent);
        }
    }
}
