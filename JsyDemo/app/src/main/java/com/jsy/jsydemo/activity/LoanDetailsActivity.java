package com.jsy.jsydemo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.moblie.zmxy.antgroup.creditsdk.app.CreditApp;
import com.android.moblie.zmxy.antgroup.creditsdk.app.ICreditListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.LoanDatailsData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.OperatorActivity;
import com.jsy.jsydemo.activity.personaldata.PersonalDataCertificatesActivity;
import com.jsy.jsydemo.adapter.LoansupemarketGridviewAdapter;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.webview.LoanWebViewActivity;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;

/**
 * Created by vvguoliang on 2017/7/4.
 * 贷款详情
 */
@SuppressLint("SetTextI18n")
public class LoanDetailsActivity extends BaseActivity implements View.OnClickListener, DataCallBack {

    private EditText loan_details_editText_range;
    private TextView loan_details_textView_rang;

    private EditText loan_details_editText_day;
    private TextView loan_details_textView_day;

    private Button loan_details_basic_information;
    private Button loan_details_phone_operator;
    private Button loan_details_id;
    private Button loan_details_other;

    private Button oan_details_esame_credit;

    private Button loan_details_button;

    private TextView details_editText_day_text;

    private ImageView loan_details_image;
    private TextView loan_details_text_name;
    private GridView loan_details_gridView;
    private TextView loan_details_text;

    private String id = "";

    private double loanMax = 0;

    private double loanMin = 0;

    private double day_monthMax = 0;

    private double day_monthMin = 0;

    private LoanDatailsData loanDatailsData;

    private LinearLayout basic_information_linear;
    private LinearLayout phone_operator_linear;
    private LinearLayout esame_credit_linear;
    private LinearLayout etails_id_linear;
    private LinearLayout details_other_linear;

    private Intent intent = null;

    private CreditApp creditApp;

    private String username = "";

    private boolean phone_operator = false;
    private boolean basic_information = false;
    private boolean details_id = false;
    private boolean details_other = false;
    private boolean credit_linear = false;

    private LinearLayout view_information;
    private LinearLayout product_details_line;
    private RelativeLayout evaluate_line;

    private TextView details_information_line;
    private TextView details_evaluate_line;
    private View view_infor1;
    private View view_infor2;


    private boolean Apitype = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.act_loandetails );
        creditApp = CreditApp.getOrCreateInstance( this.getApplicationContext() );
        username = SharedPreferencesUtils.get( this, "username", "" ).toString();
        id = getIntent().getExtras().getString( "id" );
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B( this );
        }
        getfindview();
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.phone_operator_linear:
            case R.id.loan_details_phone_operator://运营商
                startActivityForResult( new Intent( LoanDetailsActivity.this, OperatorActivity.class ), 1000 );
                break;
            case R.id.basic_information_linear:
            case R.id.loan_details_basic_information://基础信息认证
                startActivityForResult( new Intent( LoanDetailsActivity.this, BasicAuthenticationActivity.class ), 1001 );
                break;
            case R.id.loan_details_id://身份证
                startActivityForResult( new Intent( LoanDetailsActivity.this, PersonalDataCertificatesActivity.class ), 1002 );
                break;
            case R.id.details_other_linear:
            case R.id.loan_details_other://其他
                if (loanDatailsData != null) {
                    intent = new Intent( LoanDetailsActivity.this, OtherInformationActivity.class );
                    intent.putExtra( "other", loanDatailsData.getOther_id() );
                    intent.putExtra( "pid", id );
                    startActivityForResult( intent, 1003 );
                }
                break;
            case R.id.loan_details_button://补齐
                if (loanDatailsData != null) {
                    if (basic_information_linear.getVisibility() == View.GONE) {
                        basic_information = true;
                    }
                    if (phone_operator_linear.getVisibility() == View.GONE) {
                        phone_operator = true;
                    }
                    if (esame_credit_linear.getVisibility() == View.GONE) {
                        credit_linear = true;
                    }
                    if (etails_id_linear.getVisibility() == View.GONE) {
                        details_id = true;
                    }
                    if (details_other_linear.getVisibility() == View.GONE) {
                        details_other = true;
                    }
                    if (phone_operator && basic_information && details_id && details_other && credit_linear) {
                        intent = new Intent( LoanDetailsActivity.this, LoanWebViewActivity.class );
                        intent.putExtra( "url", loanDatailsData.getPro_link() );
                        startActivity( intent );
                    } else {
                        ToatUtils.showShort1( this, "还有信息没有认证完,请认证完再点击" );
                    }
                } else {
                    ToatUtils.showShort1( this, "还有信息没有认证完,请认证完再点击" );
                }
                break;
            case R.id.esame_credit_linear:
            case R.id.oan_details_esame_credit://芝麻信用
                getSesameCredit();
                break;
            case R.id.details_information_line:
                getVISI( 1 );
                break;
            case R.id.details_evaluate_line:
                getVISI( 2 );
                break;
        }
    }

    /**
     * 控制 显示或者隐藏
     */
    private void getfindview() {
        view_information = findViewById( R.id.view_information );
        product_details_line = findViewById( R.id.product_details_line );
        evaluate_line = findViewById( R.id.evaluate_line );

        details_information_line = findViewById( R.id.details_information_line );
        details_evaluate_line = findViewById( R.id.details_evaluate_line );

        view_infor1 = findViewById( R.id.view_infor1 );
        view_infor2 = findViewById( R.id.view_infor2 );

        details_information_line.setOnClickListener( this );
        details_evaluate_line.setOnClickListener( this );
    }

    @Override
    protected void findViewById() {
        findViewById( R.id.title_image ).setVisibility( View.VISIBLE );
        findViewById( R.id.title_image ).setOnClickListener( this );

        TextView title_view = findViewById( R.id.title_view );
        title_view.setText( this.getString( R.string.name_loan_details ) );

        loan_details_editText_range = findViewById( R.id.loan_details_editText_range );
        loan_details_textView_rang = findViewById( R.id.loan_details_textView_rang );
        loan_details_editText_day = findViewById( R.id.loan_details_editText_day );
        loan_details_textView_day = findViewById( R.id.loan_details_textView_day );

//        loan_details_resource_rat = (TextView) findViewById(R.id.loan_details_resource_rat);
//        loan_details_repayment = (TextView) findViewById(R.id.loan_details_repayment);
//        loan_details_loan_time = (TextView) findViewById(R.id.loan_details_loan_time);

        loan_details_basic_information = findViewById( R.id.loan_details_basic_information );
        loan_details_basic_information.setOnClickListener( this );
        loan_details_phone_operator = findViewById( R.id.loan_details_phone_operator );
        loan_details_phone_operator.setOnClickListener( this );
        loan_details_id = findViewById( R.id.loan_details_id );
        loan_details_id.setOnClickListener( this );
        loan_details_other = findViewById( R.id.loan_details_other );
        loan_details_other.setOnClickListener( this );
        oan_details_esame_credit = findViewById( R.id.oan_details_esame_credit );
        oan_details_esame_credit.setOnClickListener( this );
        loan_details_button = findViewById( R.id.loan_details_button );
        loan_details_button.setOnClickListener( this );

//        details_repayment_text = (TextView) findViewById(R.id.details_repayment_text);
        details_editText_day_text = findViewById( R.id.details_editText_day_text );

        loan_details_image = findViewById( R.id.loan_details_image );
        loan_details_text_name = findViewById( R.id.loan_details_text_name );
        loan_details_gridView = findViewById( R.id.loan_details_gridView );
        loan_details_text = findViewById( R.id.loan_details_text );


        loan_details_editText_range.addTextChangedListener( textWatcher( 1 ) );
        loan_details_editText_day.addTextChangedListener( textWatcher( 2 ) );

        basic_information_linear = findViewById( R.id.basic_information_linear );
        phone_operator_linear = findViewById( R.id.phone_operator_linear );
        esame_credit_linear = findViewById( R.id.esame_credit_linear );
        etails_id_linear = findViewById( R.id.etails_id_linear );
        details_other_linear = findViewById( R.id.details_other_linear );

        basic_information_linear.setOnClickListener( this );
        phone_operator_linear.setOnClickListener( this );
        esame_credit_linear.setOnClickListener( this );
        etails_id_linear.setOnClickListener( this );
        details_other_linear.setOnClickListener( this );
    }

    @Override
    protected void initView() {
    }

    private void getHttp() {
        Map<String, Object> map = new HashMap<>();
        map.put( "uid", SharedPreferencesUtils.get( this, "uid", "" ).toString() );
        map.put( "id", Long.parseLong( id ) );
        OkHttpManager.postAsync( HttpURL.getInstance().PRODUCT_DETAIL, "product_detail", map, this );
    }

    private void getHttpcommentlist() {
        Map<String, Object> map = new HashMap<>();
        map.put( "id", Long.parseLong( id ) );
        map.put( "page", 1 );
        map.put( "page_size", 10 );
        OkHttpManager.postAsync( HttpURL.getInstance().COMMENTLIST, "comment_list", map, this );
    }

    private void getSesameCredit() {
        Map<String, Object> map = new HashMap<>();
        map.put( "identity_type", "1" );
        JSONObject id = new JSONObject();
        try {
            id.put( "mobileNo", username );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put( "identity_param", id.toString() );
        OkHttpManager.postAsync( HttpURL.getInstance().SESAMECREDIT, "SesameCredit", map, this );
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        ToatUtils.showShort1( this, this.getString( R.string.network_timed ) );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "product_detail":
                loanDatailsData = JsonData.getInstance().getJsonLoanDailsData( result );
                Glide.with( this )
                        .load( HttpURL.getInstance().HTTP_URL_PATH + loanDatailsData.getImg() )
                        .listener( new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                                loan_details_image.setImageResource( R.mipmap.ic_path_in_load );
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                                return false;
                            }
                        } )
                        .into( loan_details_image );
                loan_details_text_name.setText( loanDatailsData.getPro_name() );
                loan_details_text.setText( this.getString( R.string.name_loan_product_interest_rat ) + loanDatailsData.getFeilv() );
                SpannableStringBuilder builder = new SpannableStringBuilder( loan_details_text.getText().toString() );
                //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                ForegroundColorSpan redSpan = new ForegroundColorSpan( Color.parseColor( "#004eb7" ) );
                builder.setSpan( redSpan, 3, loan_details_text.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                loan_details_text.setText( builder );

                LoansupemarketGridviewAdapter loansupemarketGridviewAdapter = new LoansupemarketGridviewAdapter( this );
                List<String> list = new ArrayList<>();
                JSONArray object1 = new JSONArray( loanDatailsData.getTags() );
                for (int i = 0; object1.length() > i; i++) {
                    JSONObject jsonObject = object1.optJSONObject( i );
                    list.add( jsonObject.optString( "tag_name" ) );
                }
                loansupemarketGridviewAdapter.GridviewAdapter( list );
                loan_details_gridView.setAdapter( loansupemarketGridviewAdapter );
                loansupemarketGridviewAdapter.notifyDataSetChanged();

                if (loanDatailsData.getFv_unit().equals( "1" )) {//天
                    details_editText_day_text.setText( this.getString( R.string.name_loan_details_month ) );
                } else {
                    details_editText_day_text.setText( this.getString( R.string.name_loan_details_day ) );

                }
                loan_details_textView_rang.setText( "额度范围" + loanDatailsData.getEdufanwei() );

                String[] edufan;
                if (loanDatailsData.getEdufanwei().contains( "," )) {
                    edufan = loanDatailsData.getEdufanwei().split( "," );
                } else {
                    edufan = loanDatailsData.getEdufanwei().split( "-" );
                }
                loanMax = Double.parseDouble( edufan[1] );
                loanMin = Double.parseDouble( edufan[0] );
                loan_details_editText_range.setText( edufan[1] );

                loan_details_textView_day.setText( "期限范围" + loanDatailsData.getQixianfanwei() );
                String[] edufan1;
                if (loanDatailsData.getQixianfanwei().contains( "," )) {
                    edufan1 = loanDatailsData.getQixianfanwei().split( "," );
                } else {
                    edufan1 = loanDatailsData.getQixianfanwei().split( "-" );
                }
                day_monthMax = Double.parseDouble( edufan1[1] );
                day_monthMin = Double.parseDouble( edufan1[0] );
                loan_details_editText_day.setText( edufan1[1] );

                //判断认证还是产品详情
                if (!TextUtils.isEmpty( loanDatailsData.getApi_type() ) && loanDatailsData.getApi_type().equals( "3" )) {//认证
                    details_information_line.setText( getString( R.string.name_loan_details_information ) );
                    Apitype = false;
                    view_information.setVisibility( View.VISIBLE );
                    product_details_line.setVisibility( View.GONE );
                } else {
                    Apitype = true;
                    details_information_line.setText( getString( R.string.name_loan_details_product_details ) );
                    view_information.setVisibility( View.GONE );
                    product_details_line.setVisibility( View.VISIBLE );
                }

                if (TextUtils.isEmpty( loanDatailsData.getUser_auth() ) || loanDatailsData.getUser_auth().equals( "null" )
                        || loanDatailsData.getUser_auth().equals( "0" )) {
                    loan_details_basic_information.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                    loan_details_phone_operator.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                    loan_details_id.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                    loan_details_other.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                    oan_details_esame_credit.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                } else {
                    JSONObject object = new JSONObject( loanDatailsData.getUser_auth() );
                    if (!TextUtils.isEmpty( object.optString( "base_auth" ) ) && object.optString( "base_auth" ).equals( "1" )) {
                        basic_information = true;
                        loan_details_basic_information.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
                    } else {
                        basic_information = false;
                        loan_details_basic_information.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                    }
                    if (!TextUtils.isEmpty( object.optString( "mobile_auth" ) ) && object.optString( "mobile_auth" ).equals( "1" )) {
                        phone_operator = true;
                        loan_details_phone_operator.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
                    } else {
                        phone_operator = false;
                        loan_details_phone_operator.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                    }
                    if (!TextUtils.isEmpty( object.optString( "zhima_auth" ) ) && object.optString( "zhima_auth" ).equals( "1" )) {
                        credit_linear = true;
                        oan_details_esame_credit.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
                    } else {
                        credit_linear = false;
                        oan_details_esame_credit.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                    }
                    if (!TextUtils.isEmpty( object.optString( "idcard_auth" ) ) && object.optString( "idcard_auth" ).equals( "1" )) {
                        details_id = true;
                        loan_details_id.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
                    } else {
                        details_id = false;
                        loan_details_id.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                    }
                }
                if (!TextUtils.isEmpty( loanDatailsData.getOther_auth() ) && loanDatailsData.getOther_auth().equals( "1" )) {
                    details_other = true;
                    loan_details_other.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
                } else {
                    details_other = false;
                    loan_details_other.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                }
                String data_id = loanDatailsData.getData_id();
                Pattern pattern = Pattern.compile( "\\[(.*)\\]" );
                Matcher matcher = pattern.matcher( data_id );
                String ResponseDates = "";
                while (matcher.find()) {
                    ResponseDates = matcher.group( 1 );
                }
                String[] data_ids = ResponseDates.split( "," );
                for (String data_id1 : data_ids) {
                    if (data_id1.contains( "1" )) {
                        basic_information_linear.setVisibility( View.VISIBLE );
                    }
                    if (data_id1.contains( "2" )) {
                        phone_operator_linear.setVisibility( View.VISIBLE );
                    }
                    if (data_id1.contains( "3" )) {
                        esame_credit_linear.setVisibility( View.VISIBLE );
                    }
                    if (data_id1.contains( "4" )) {
                        etails_id_linear.setVisibility( View.VISIBLE );
                    }
                    if (data_id1.contains( "5" )) {
                        details_other_linear.setVisibility( View.VISIBLE );
                    }
                }
                break;
            case "SesameCredit":
                JSONObject object = new JSONObject( result );
                JSONObject jsonObject = new JSONObject( object.optString( "data" ) );
                String params = "";
                String sign = "";
                if (jsonObject.has( "params" )) {
                    params = jsonObject.optString( "params" );
                }
                if (jsonObject.has( "sign" )) {
                    sign = jsonObject.optString( "sign" );
                }
                Map extParams = new HashMap<>();
                creditApp.authenticate( this, "1002755", "", params, sign, extParams, iCreditListener );
                break;
            case "authorize":
                if (result.contains( "成功" )) {
                    credit_linear = true;
                    oan_details_esame_credit.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
                } else {
                    credit_linear = false;
                    oan_details_esame_credit.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
                }
                break;
            case "comment_list":
                Log.e( "", "============" + result );
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getHttp();
        getHttpcommentlist();
        MobclickAgent.onResume( this );
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause( this );
    }

    /**
     * 动态金额显示
     */
    private TextWatcher textWatcher(final int i) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (i) {
                    case 1:
                        if (!StringUtil.isNullOrEmpty( s.toString() )) {
                            if (Double.parseDouble( s.toString() ) > loanMax || Double.parseDouble( s.toString() ) < loanMin) {
                                ToatUtils.showShort1( LoanDetailsActivity.this, "您输入的金额已经超出范围" );
                                loan_details_editText_range.setText( "" + (int) loanMax );
                            }
//                            else {
//                                if (anInt != 0) {
//                                    double dou = Double.parseDouble(loan_details_editText_day.getText().toString());
//                                    loan_details_repayment.setText(getAlgorithm(Double.parseDouble(s.toString()), dou, profit));
//                                }
//                            }
                        } else {
                            loan_details_editText_range.setText( "0" );
                        }
                        break;
                    case 2:
                        if (!StringUtil.isNullOrEmpty( s.toString() )) {
                            if (Double.parseDouble( s.toString() ) > day_monthMax || Double.parseDouble( s.toString() ) < day_monthMin) {
                                if (loanDatailsData != null && !StringUtil.isNullOrEmpty( loanDatailsData.getFv_unit() ) &&
                                        loanDatailsData.getFv_unit().equals( "1" )) {
                                    ToatUtils.showShort1( LoanDetailsActivity.this, "您输入的月份已经超出范围" );
                                    loan_details_editText_day.setText( "" + (int) day_monthMax );
                                } else {
                                    ToatUtils.showShort1( LoanDetailsActivity.this, "您输入的天数已经超出范围" );
                                    loan_details_editText_day.setText( "" + (int) day_monthMax );
                                }
                            }
//                            else {
//                                if (anInt != 0) {
//                                    double dou = Double.parseDouble(loan_details_editText_range.getText().toString());
//                                    loan_details_repayment.setText(getAlgorithm(dou, Double.parseDouble(s.toString()), profit));
//                                }
//                            }
                        } else {
                            loan_details_editText_day.setText( "0" );
                        }
                        break;
                }
            }
        };
        return textWatcher;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        String datastring = "";
        if (requestCode == 1000) {
            datastring = data.getStringExtra( "operator" );
            if (!TextUtils.isEmpty( datastring ) && datastring.equals( "1" )) {
                phone_operator = true;
                loan_details_phone_operator.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
            } else {
                phone_operator = false;
                loan_details_phone_operator.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
            }
            return;
        } else if (requestCode == 1001) {
            datastring = data.getStringExtra( "operator" );
            if (!TextUtils.isEmpty( datastring ) && datastring.equals( "1" )) {
                basic_information = true;
                loan_details_basic_information.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
            } else {
                basic_information = false;
                loan_details_basic_information.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
            }
            return;
        } else if (requestCode == 1002) {
            datastring = data.getStringExtra( "operator" );
            if (!TextUtils.isEmpty( datastring ) && datastring.equals( "1" )) {
                details_id = true;
                loan_details_id.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
            } else {
                details_id = false;
                loan_details_id.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
            }
            return;
        } else if (requestCode == 1003) {
            datastring = data.getStringExtra( "operator" );
            if (!TextUtils.isEmpty( datastring ) && datastring.equals( "1" )) {
                details_other = true;
                loan_details_other.setBackgroundResource( R.mipmap.ic_loan_details_authentication );
            } else {
                details_other = false;
                loan_details_other.setBackgroundResource( R.mipmap.ic_loan_detail_no_authentication );
            }
            return;
        }
        CreditApp.onActivityResult( requestCode, resultCode, data );
    }

    ICreditListener iCreditListener = new ICreditListener() {
        @Override
        public void onComplete(Bundle result) {
            credit_linear = true;
            Set keys = result.keySet();
            Map<String, Object> map = new HashMap<>();
            for (Object key : keys) {
                map.put( key.toString(), result.getString( key.toString() ) );
            }
            map.put( "uid", SharedPreferencesUtils.get( LoanDetailsActivity.this, "uid", "" ).toString() );
            OkHttpManager.postAsync( HttpURL.getInstance().AUTHORIZE, "authorize", map, LoanDetailsActivity.this );

        }

        @Override
        public void onError(Bundle error) {
            credit_linear = false;
            Set keys = error.keySet();
            Map<String, Object> map = new HashMap<>();
            for (Object key : keys) {
                map.put( key.toString(), error.getString( key.toString() ) );
            }
            map.put( "uid", SharedPreferencesUtils.get( LoanDetailsActivity.this, "uid", "" ).toString() );
            OkHttpManager.postAsync( HttpURL.getInstance().AUTHORIZE, "authorize", map, LoanDetailsActivity.this );
        }

        @Override
        public void onCancel() {
            Log.d( "", "" );
        }
    };

    /**
     * 控制点击事件 操作显示那个页面
     *
     * @param i
     */
    private void getVISI(int i) {
        switch (i) {
            case 1:
                if (Apitype) {
                    view_information.setVisibility( View.GONE );
                    product_details_line.setVisibility( View.VISIBLE );
                } else {
                    view_information.setVisibility( View.VISIBLE );
                    product_details_line.setVisibility( View.GONE );
                }
                product_details_line.setVisibility( View.GONE );
                view_infor1.setBackgroundColor( Color.parseColor( "#78B2EE" ) );
                view_infor2.setBackgroundColor( Color.parseColor( "#a6a6a6" ) );
                details_information_line.setTextColor( Color.parseColor( "#78B2EE" ) );
                details_evaluate_line.setTextColor( Color.parseColor( "#a6a6a6" ) );
                break;
            case 2:
                product_details_line.setVisibility( View.VISIBLE );
                view_information.setVisibility( View.GONE );
                product_details_line.setVisibility( View.GONE );
                view_infor2.setBackgroundColor( Color.parseColor( "#78B2EE" ) );
                view_infor1.setBackgroundColor( Color.parseColor( "#a6a6a6" ) );
                details_evaluate_line.setTextColor( Color.parseColor( "#78B2EE" ) );
                details_information_line.setTextColor( Color.parseColor( "#a6a6a6" ) );
                break;
        }
    }
}