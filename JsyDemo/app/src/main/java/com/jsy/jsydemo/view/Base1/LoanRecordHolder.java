package com.jsy.jsydemo.view.Base1;

import android.content.Context;
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
import com.jsy.jsydemo.EntityClass.LoanRecordBand;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.view.BaseViewHolder;

/**
 * Created by vvguoliang on 2017/9/1.
 */

public class LoanRecordHolder extends BaseViewHolder<LoanRecordBand> {

    private TextView record_loan_time;
    private ImageView record_loan_img;
    private TextView record_loan_name;
    private TextView record_loan_money;
    private TextView record_loan_day;
    private TextView record_loan_end;
    private Button record_loan_button;

    private ViewGroup parent;

    public LoanRecordHolder(Context context, ViewGroup parent) {
        super( context, parent, R.layout.holder_loan_record );
        this.parent = parent;
    }

    @Override
    public void setData(LoanRecordBand data) {
        super.setData( data );
        if (data != null) {
            Glide.with( parent.getContext() )
                    .load( data.getImg() )
                    .listener( new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                            record_loan_img.setImageResource( R.mipmap.ic_path_in_load );
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                            return false;
                        }
                    } )
                    .into( record_loan_img );
            record_loan_name.setText( data.getPro_name() );
//            record_loan_money.setText( data.getPro_describe() );
            record_loan_end.setText( data.getCreated_at() );
        }
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        record_loan_time = findViewById( R.id.record_loan_time );
        record_loan_img = findViewById( R.id.record_loan_img );
        record_loan_name = findViewById( R.id.record_loan_name );
        record_loan_money = findViewById( R.id.record_loan_money );
        record_loan_day = findViewById( R.id.record_loan_day );
        record_loan_end = findViewById( R.id.record_loan_end );
        record_loan_button = findViewById( R.id.record_loan_button );
    }

    @Override
    public void onItemViewClick(LoanRecordBand data) {
        super.onItemViewClick( data );
        ToatUtils.showShort1( parent.getContext(), "你想去哪里？" );
    }
}
