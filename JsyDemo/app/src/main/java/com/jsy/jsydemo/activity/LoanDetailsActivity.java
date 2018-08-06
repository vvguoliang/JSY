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
import android.widget.TextView;

import com.android.moblie.zmxy.antgroup.creditsdk.app.CreditApp;
import com.android.moblie.zmxy.antgroup.creditsdk.app.ICreditListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jsy.jsydemo.EntityClass.EvaluateData;
import com.jsy.jsydemo.EntityClass.LoanDatailsData;
import com.jsy.jsydemo.R;
import com.jsy.jsydemo.activity.helpFeedbackFriendsMyPackage.OperatorActivity;
import com.jsy.jsydemo.activity.personaldata.PersonalDataCertificatesActivity;
import com.jsy.jsydemo.adapter.LoansupemarketGridviewAdapter;
import com.jsy.jsydemo.base.BaseActivity;
import com.jsy.jsydemo.control.MyButton;
import com.jsy.jsydemo.http.http.i.DataCallBack;
import com.jsy.jsydemo.http.http.i.httpbase.HttpURL;
import com.jsy.jsydemo.http.http.i.httpbase.OkHttpManager;
import com.jsy.jsydemo.utils.AppUtil;
import com.jsy.jsydemo.utils.CommonAdapter;
import com.jsy.jsydemo.utils.ImmersiveUtils;
import com.jsy.jsydemo.utils.JsonData;
import com.jsy.jsydemo.utils.SharedPreferencesUtils;
import com.jsy.jsydemo.utils.StringUtil;
import com.jsy.jsydemo.utils.ToatUtils;
import com.jsy.jsydemo.utils.ViewHolder;
import com.jsy.jsydemo.view.Base1.RatingBar;
import com.jsy.jsydemo.view.LoadListView;
import com.jsy.jsydemo.webview.LoanWebViewActivity;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
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
public class LoanDetailsActivity extends BaseActivity implements View.OnClickListener, DataCallBack, LoadListView.IloadListener {

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

    private int day_monthMax = 0;

    private int day_monthMin = 0;

    private boolean have_date = false;

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

    private LinearLayout view_information;//贷款流程
    private LinearLayout product_details_line;//产品详情
    private TextView details_information_line;//认证资料

    private TextView details_evaluate_line;
    private View view_infor1;
    private View view_infor2;
    private String apiType;
    private boolean Apitype = false;

    //    private RecyclerView evaluate_refreshRecyclerView;//评价
    private LinearLayout evaluate_line_layout;
    private List<EvaluateData> evaluateDataList = new ArrayList<>();
    private EvaluateData[] evaluateDataArr;
    private MyButton evaluateComBt;//评价提交
    private int evalulate_pate = 0;
    private int evaluate_totle_page = 1;
    //    private ListView listView;
    private LoadListView listView;
    private CommonAdapter<EvaluateData> EvaluateListAdapter;
    private String[] dayArr;
    private int loan_day_type = 0;

    private String status = "0";

    //产品详情
    private TextView audit_text, audit1, audit2, audit3, audit4, audit5, audit6, audit7, audit8, audit9, audit10, audit11, guid_text;
    private List<Map<String, String>> details_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loandetails);
        creditApp = CreditApp.getOrCreateInstance(this.getApplicationContext());
        username = SharedPreferencesUtils.get(this, "username", "").toString();
        id = getIntent().getExtras().getString("id");
        apiType = getIntent().getExtras().getString("Api_type");
//        apiType = "3";
        //沉浸式状态设置
        if (ImmersiveUtils.BuildVERSION()) {
            ImmersiveUtils.getInstance().getW_add_B(this);
        }
        getfindview();
        findViewById();
        initView();
        initViewForevaluate();//评价
    }

    private void initViewForevaluate() {
        //接手后做的的下部列表数据
        //评价
        listView = findViewById(R.id.list_view);//评论listView
        evaluateComBt = findViewById(R.id.evaluate_line_button2);//评论按钮
        evaluateComBt.setOnClickListener(this);
        findViewById(R.id.loan_service_bt).setOnClickListener(this);//客服功能

        //产品详情
        audit_text = findViewById(R.id.audit_text);
        audit1 = findViewById(R.id.audit_text1);
        audit2 = findViewById(R.id.audit_text2);
        audit3 = findViewById(R.id.audit_text3);
        audit4 = findViewById(R.id.audit_text4);
        audit5 = findViewById(R.id.audit_text5);
        audit6 = findViewById(R.id.audit_text6);
        audit7 = findViewById(R.id.audit_text7);
        audit8 = findViewById(R.id.audit_text8);
        audit9 = findViewById(R.id.audit_text9);
        audit10 = findViewById(R.id.audit_text10);
        audit11 = findViewById(R.id.audit_text11);
        guid_text = findViewById(R.id.guid_text);
        EvaluateListAdapter = new CommonAdapter<EvaluateData>(this, evaluateDataList, R.layout.view_evaluate_recycler_item) {
            @Override
            protected void convertlistener(ViewHolder holder, EvaluateData evaluateData) {
            }

            @Override
            public void convert(ViewHolder holder, EvaluateData data) {
                if (data != null) {

                    String phoneNum = data.getMobile() + "";

//                    StringBuffer  stringBuffer = null;
//                    char[] phone ;
//                    phone = data.getMobile().toCharArray();
//                    if (phone!=null && !data.getMobile().equals("null") &&phone.length>=11) {
//                        for (int i = 3; i<7;i++){
//                            phone[i] = '*';
//                        }
//                        for (Character a:phone){
//                            stringBuffer.append(a);
//                        }
//                        holder.getTextview(R.id.evaluate_recycler_user_name).setText(stringBuffer.toString()+ "");
//                    }else {
//                        holder.getTextview(R.id.evaluate_recycler_user_name).setText(data.getMobile()+"");
//                    }
                    if (phoneNum != null && !phoneNum.equals("null") && phoneNum.length() > 10) {
                        phoneNum = data.getMobile().substring(0, 3) + "****" + data.getMobile().substring(7, data.getMobile().length());
                        holder.getTextview(R.id.evaluate_recycler_user_name).setText(phoneNum + "");
                    } else {
                        holder.getTextview(R.id.evaluate_recycler_user_name).setText(data.getMobile() + "");
                    }

                    String type = data.getType() + "";
                    if (type.equals("1")) {
                        holder.getTextview(R.id.evaluate_recycler_user_isloan1).setVisibility(View.VISIBLE);
                        holder.getTextview(R.id.evaluate_recycler_user_isloan2).setVisibility(View.GONE);
                    } else {
                        holder.getTextview(R.id.evaluate_recycler_user_isloan2).setVisibility(View.VISIBLE);
                        holder.getTextview(R.id.evaluate_recycler_user_isloan1).setVisibility(View.GONE);
                    }
                    holder.getTextview(R.id.evaluate_recycler_user_va).setText(data.getComment() + "");
                    holder.getTextview(R.id.evaluate_recycler_user_time).setText(data.getCreated_at() + "");
                    RatingBar ratingBar = holder.getView(R.id.evaluate_recycler_user_ratingbar);
                    ratingBar.setStar(Float.parseFloat(data.getScore() + ""));
                }
            }
        };
        listView.setAdapter(EvaluateListAdapter);


        listView.setInterface(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_image:
                finish();
                break;
            case R.id.phone_operator_linear:
            case R.id.loan_details_phone_operator://运营商
                startActivityForResult(new Intent(LoanDetailsActivity.this, OperatorActivity.class), 1000);
                break;
            case R.id.basic_information_linear:
            case R.id.loan_details_basic_information://基础信息认证
                startActivityForResult(new Intent(LoanDetailsActivity.this, BasicAuthenticationActivity.class), 1001);
                break;
            case R.id.loan_details_id://身份证
                startActivityForResult(new Intent(LoanDetailsActivity.this, PersonalDataCertificatesActivity.class), 1002);
                break;
            case R.id.details_other_linear:
            case R.id.loan_details_other://其他
                if (loanDatailsData != null) {
                    intent = new Intent(LoanDetailsActivity.this, OtherInformationActivity.class);
                    intent.putExtra("other", loanDatailsData.getOther_id());
                    intent.putExtra("pid", id);
                    startActivityForResult(intent, 1003);
                }
                break;
            case R.id.loan_details_button://补齐
                if (!apiType.equals("3")) {
                    if (isMountAndTimeRight() == 2) {
                        try {
                            postLoanRecord();//借款记录
                            intent = new Intent(LoanDetailsActivity.this, LoanWebViewActivity.class);
                            intent.putExtra("url", loanDatailsData.getPro_link());
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                    }

                } else {
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

                            if (isMountAndTimeRight() == 2) {
                                postLoanRecord();//借款记录
                                intent = new Intent(LoanDetailsActivity.this, LoanWebViewActivity.class);
                                intent.putExtra("url", loanDatailsData.getPro_link());
                                startActivity(intent);
                            }

                        } else {
                            ToatUtils.showShort1(this, "还有信息没有认证完,请认证完再点击");
                        }
                    } else {
                        ToatUtils.showShort1(this, "还有信息没有认证完,请认证完再点击");
                    }
                }
                break;
            case R.id.esame_credit_linear:
            case R.id.oan_details_esame_credit://芝麻信用
                getSesameCredit();
                break;
            case R.id.details_information_line:
                getVISI(1);
                break;
            case R.id.details_evaluate_line:
                getVISI(2);
                break;
            case R.id.loan_service_bt://客服功能
                HashMap<String, String> clientInfo = new HashMap<>();
                clientInfo.put("name", SharedPreferencesUtils.get(this, "username", "").toString());
                clientInfo.put("avatar", "https://s3.cn-north-1.amazonaws.com.cn/pics.meiqia.bucket/1dee88eabfbd7bd4");
                clientInfo.put("id", id + "");
                clientInfo.put("uid", SharedPreferencesUtils.get(this, "uid", "").toString());
                clientInfo.put("source", "android  及时雨");
                Intent intent = new MQIntentBuilder(this)
                        .setCustomizedId(SharedPreferencesUtils.get(this, "uid", "").toString())
                        .setClientInfo(clientInfo)
                        .build();
                startActivity(intent);

                break;
            case R.id.evaluate_line_button2://发表评论
                Intent submitIntent = new Intent(LoanDetailsActivity.this, SubmitEvaluateActiviay.class);
                submitIntent.putExtra("pid", id);
                this.startActivity(submitIntent);

                break;
        }
    }

    /**
     * 控制 显示或者隐藏
     */
    private void getfindview() {
        view_information = findViewById(R.id.view_information_layout);//
//        product_details_line = findViewById( R.layout.view_product_details_listview );//产品详情
        product_details_line = findViewById(R.id.view_product_details_listview_layout);//产品详情
        evaluate_line_layout = findViewById(R.id.evaluate_line_layout);//评价
        details_information_line = findViewById(R.id.details_information_line);
        details_evaluate_line = findViewById(R.id.details_evaluate_line);

        view_infor1 = findViewById(R.id.view_infor1);
        view_infor2 = findViewById(R.id.view_infor2);

        details_information_line.setOnClickListener(this);
        details_evaluate_line.setOnClickListener(this);

        product_or_evaluate();
    }

    private void product_or_evaluate() {
        if (apiType.equals("3")) {//用户评价  和  认证资料
            evaluate_line_layout.setVisibility(View.GONE);
            view_information.setVisibility(View.VISIBLE);
//            view_infor1.setVisibility(View.GONE);//左边下划线
//            details_information_line.setVisibility(View.GONE);//左边textView

//            view_infor1.setBackgroundColor(Color.parseColor("#78B2EE"));
//            details_information_line.setTextColor(Color.parseColor("#78B2EE"));
//            details_evaluate_line.setTextColor(Color.parseColor("#a6a6a6"));
//            view_infor2.setBackgroundColor(Color.parseColor("#a6a6a6"));

        } else {

            evaluate_line_layout.setVisibility(View.GONE);
            product_details_line.setVisibility(View.VISIBLE);
        }
        details_evaluate_line.setTextColor(Color.parseColor("#a6a6a6"));
        view_infor2.setBackgroundColor(Color.parseColor("#a6a6a6"));
        details_information_line.setTextColor(Color.parseColor("#78B2EE"));
        view_infor1.setBackgroundColor(Color.parseColor("#78B2EE"));
    }


    @Override
    protected void findViewById() {
        findViewById(R.id.title_image).setVisibility(View.VISIBLE);
        findViewById(R.id.title_image).setOnClickListener(this);

        TextView title_view = findViewById(R.id.title_view);
        title_view.setText(this.getString(R.string.name_loan_details));

        loan_details_editText_range = findViewById(R.id.loan_details_editText_range);
        loan_details_textView_rang = findViewById(R.id.loan_details_textView_rang);
        loan_details_editText_day = findViewById(R.id.loan_details_editText_day);
        loan_details_textView_day = findViewById(R.id.loan_details_textView_day);

//        loan_details_resource_rat = (TextView) findViewById(R.id.loan_details_resource_rat);
//        loan_details_repayment = (TextView) findViewById(R.id.loan_details_repayment);
//        loan_details_loan_time = (TextView) findViewById(R.id.loan_details_loan_time);

        loan_details_basic_information = findViewById(R.id.loan_details_basic_information);
        loan_details_basic_information.setOnClickListener(this);
        loan_details_phone_operator = findViewById(R.id.loan_details_phone_operator);
        loan_details_phone_operator.setOnClickListener(this);
        loan_details_id = findViewById(R.id.loan_details_id);
        loan_details_id.setOnClickListener(this);
        loan_details_other = findViewById(R.id.loan_details_other);
        loan_details_other.setOnClickListener(this);
        oan_details_esame_credit = findViewById(R.id.oan_details_esame_credit);
        oan_details_esame_credit.setOnClickListener(this);
        loan_details_button = findViewById(R.id.loan_details_button);
        loan_details_button.setOnClickListener(this);

//        details_repayment_text = (TextView) findViewById(R.id.details_repayment_text);
        details_editText_day_text = findViewById(R.id.details_editText_day_text);

        loan_details_image = findViewById(R.id.loan_details_image);
        loan_details_text_name = findViewById(R.id.loan_details_text_name);
        loan_details_gridView = findViewById(R.id.loan_details_gridView);
        loan_details_text = findViewById(R.id.loan_details_text);


        loan_details_editText_range.addTextChangedListener(textWatcher(1));
        loan_details_editText_day.addTextChangedListener(textWatcher(2));

        basic_information_linear = findViewById(R.id.basic_information_linear);
        phone_operator_linear = findViewById(R.id.phone_operator_linear);
        esame_credit_linear = findViewById(R.id.esame_credit_linear);
        etails_id_linear = findViewById(R.id.etails_id_linear);
        details_other_linear = findViewById(R.id.details_other_linear);

        basic_information_linear.setOnClickListener(this);
        phone_operator_linear.setOnClickListener(this);
        esame_credit_linear.setOnClickListener(this);
        etails_id_linear.setOnClickListener(this);
        details_other_linear.setOnClickListener(this);


    }

    @Override
    protected void initView() {
    }

    private void getHttp() {//产品详情
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedPreferencesUtils.get(this, "uid", "").toString());
        map.put("id", Long.parseLong(id));
        OkHttpManager.postAsync(HttpURL.getInstance().PRODUCT_DETAIL, "product_detail", map, this);
    }

    private void postLoanRecord() {//提交借款记录
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedPreferencesUtils.get(this, "uid", "").toString());
        map.put("pid", Long.parseLong(id));
        map.put("amount", loan_details_editText_range.getText() + "");
        map.put("deadline", loan_details_editText_day.getText() + "");
        map.put("unit", loanDatailsData.getQx_unit() + "");
        OkHttpManager.postAsync(HttpURL.getInstance().USERINFOPOSTHISTRY, "loanrecord", map, this);

    }


    private void getHttpcommentlist() {//拉取评论
        evalulate_pate = evalulate_pate + 1;
        if (evalulate_pate <= evaluate_totle_page) {
            Map<String, Object> map = new HashMap<>();
            map.put("pid", Long.parseLong(id));
            map.put("page", evalulate_pate);
            map.put("page_size", 10);
            OkHttpManager.postAsync(HttpURL.getInstance().COMMENTLIST, "comment_list", map, this);
        } else {
            if (evaluateDataList.size() == 0) {
            } else {
//                ToatUtils.showShort1(this, "没有更多了...");
            }
            listView.loadComplete();
        }
    }

    private void getSesameCredit() {//芝麻信用
        Map<String, Object> map = new HashMap<>();
        map.put("identity_type", "1");
        JSONObject id = new JSONObject();
        try {
            id.put("mobileNo", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("identity_param", id.toString());
        OkHttpManager.postAsync(HttpURL.getInstance().SESAMECREDIT, "SesameCredit", map, this);
    }

    @Override
    public void requestFailure(Request request, String name, IOException e) {
        ToatUtils.showShort1(this, this.getString(R.string.network_timed));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void requestSuccess(String result, String name) throws Exception {
        switch (name) {
            case "product_detail":
                loanDatailsData = JsonData.getInstance().getJsonLoanDailsData(result);
                Glide.with(this)
                        .load(HttpURL.getInstance().HTTP_URL_PATH + loanDatailsData.getImg())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                                loan_details_image.setImageResource(R.mipmap.ic_path_in_load);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                                return false;
                            }
                        })
                        .into(loan_details_image);
                loan_details_text_name.setText(loanDatailsData.getPro_name());
                loan_details_text.setText(this.getString(R.string.name_loan_product_interest_rat) + loanDatailsData.getFeilv());
                SpannableStringBuilder builder = new SpannableStringBuilder(loan_details_text.getText().toString());
                //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#004eb7"));
                builder.setSpan(redSpan, 3, loan_details_text.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                loan_details_text.setText(builder);

                LoansupemarketGridviewAdapter loansupemarketGridviewAdapter = new LoansupemarketGridviewAdapter(this);
                List<String> list = new ArrayList<>();
                JSONArray object1 = new JSONArray(loanDatailsData.getTags());
                for (int i = 0; object1.length() > i; i++) {
                    JSONObject jsonObject = object1.optJSONObject(i);
                    list.add(jsonObject.optString("tag_name"));
                }
                loansupemarketGridviewAdapter.GridviewAdapter(list);
                loan_details_gridView.setAdapter(loansupemarketGridviewAdapter);
                loansupemarketGridviewAdapter.notifyDataSetChanged();


                if (loanDatailsData.getQx_unit().equals("1")) {//天
                    details_editText_day_text.setText(this.getString(R.string.name_loan_details_day));
                    loan_details_textView_day.setText("期限范围：" + loanDatailsData.getQixianfanwei() + "天");
                } else {
                    details_editText_day_text.setText(this.getString(R.string.name_loan_details_month));
                    loan_details_textView_day.setText("期限范围：" + loanDatailsData.getQixianfanwei() + "月");
                }
                loan_details_textView_rang.setText("额度范围：" + loanDatailsData.getEdufanwei() + "元");

                String[] edufan = new String[2];
                boolean isHaveMin = false;
                String ed = loanDatailsData.getEdufanwei() + "";
                ed = ed.replaceAll("元", "");
                if (ed.contains(",")) {
                    edufan = ed.split(",");
                    isHaveMin = true;
                } else if (ed.contains("~")) {
                    edufan = ed.split("~");
                    isHaveMin = true;
                } else if (ed.contains("-")) {
                    edufan = ed.split("-");
                    isHaveMin = true;
                } else {
                    try {
                        loanMax = Double.parseDouble(ed);
                    } catch (Exception e) {
                        loanMax = Double.parseDouble(ed.substring(4, ed.length()));
                    }
                    loanMin = Double.parseDouble(getResources().getString(R.string.loan_min));
                    loan_details_editText_range.setText(loanMax + "");
                }
                if (isHaveMin) {
                    loanMax = Double.parseDouble(edufan[1]);
                    loanMin = Double.parseDouble(edufan[0]);
                    loan_details_editText_range.setText(edufan[1]);
                }
//期限
                String[] edufan1 = null;
                String dateString = loanDatailsData.getQixianfanwei() + "";
                dateString = dateString.replaceAll("天", "");
                dateString = dateString.replaceAll("个月", "");
                dateString = dateString.replaceAll("月", "");
                if (loanDatailsData.getQixianfanwei().contains(",")) {
                    edufan1 = dateString.split(",");
                    dayArr = edufan1;
                    have_date = true;
                    loan_day_type = 1;
                } else if (loanDatailsData.getQixianfanwei().contains("~")) {
                    edufan1 = dateString.split("~");
                    have_date = true;
                } else if (loanDatailsData.getQixianfanwei().contains("-")) {
                    edufan1 = dateString.split("-");
                    have_date = true;
                } else {
                    day_monthMax = Integer.parseInt(loanDatailsData.getQixianfanwei());
                    loan_details_editText_day.setText(day_monthMax + "");
                    day_monthMin = 0;
                }
                if (have_date) {
                    day_monthMax = Integer.parseInt(edufan1[edufan1.length - 1]);
                    day_monthMin = Integer.parseInt(edufan1[0]);
                    loan_details_editText_day.setText(day_monthMax + "");
                }

                //判断认证还是产品详情
//                if (!TextUtils.isEmpty(loanDatailsData.getApi_type()) && loanDatailsData.getApi_type().equals("3")) {//认证
                if (!TextUtils.isEmpty(loanDatailsData.getApi_type())) {//认证

                    if (apiType.equals("3")) {
                        details_information_line.setText(getString(R.string.name_loan_details_information));
                        Apitype = false;
                        view_information.setVisibility(View.VISIBLE);
                        product_details_line.setVisibility(View.GONE);
                        if (phone_operator && basic_information && details_id && details_other && credit_linear) {
                            loan_details_button.setText(R.string.loan_apply_now);
                        }

                    } else {
                        Apitype = true;
                        details_information_line.setText(getString(R.string.name_loan_details_product_details));
                        view_information.setVisibility(View.GONE);
                        product_details_line.setVisibility(View.VISIBLE);
                        loan_details_button.setText(R.string.loan_apply_now);
                    }
                }
                //基础资料认证结果
                if (TextUtils.isEmpty(loanDatailsData.getUser_auth()) || loanDatailsData.getUser_auth().equals("null")
                        || loanDatailsData.getUser_auth().equals("0")) {
                    loan_details_basic_information.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    loan_details_phone_operator.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    loan_details_id.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    loan_details_other.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    oan_details_esame_credit.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                } else {
                    JSONObject object = new JSONObject(loanDatailsData.getUser_auth());
                    if (!TextUtils.isEmpty(object.optString("base_auth")) && object.optString("base_auth").equals("1")) {
                        basic_information = true;
                        loan_details_basic_information.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
                    } else {
                        basic_information = false;
                        loan_details_basic_information.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    }
                    if (!TextUtils.isEmpty(object.optString("mobile_auth")) && object.optString("mobile_auth").equals("1")) {
                        phone_operator = true;
                        loan_details_phone_operator.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
                    } else {
                        phone_operator = false;
                        loan_details_phone_operator.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    }
                    if (!TextUtils.isEmpty(object.optString("zhima_auth")) && object.optString("zhima_auth").equals("1")) {
                        credit_linear = true;
                        oan_details_esame_credit.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
                    } else {
                        credit_linear = false;
                        oan_details_esame_credit.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    }
                    if (!TextUtils.isEmpty(object.optString("idcard_auth")) && object.optString("idcard_auth").equals("1")) {
                        details_id = true;
                        loan_details_id.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
                    } else {
                        details_id = false;
                        loan_details_id.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                    }
                }
                if (!TextUtils.isEmpty(loanDatailsData.getOther_auth()) && loanDatailsData.getOther_auth().equals("1")) {
                    details_other = true;
                    loan_details_other.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
                } else {
                    details_other = false;
                    loan_details_other.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                }
                if (phone_operator && basic_information && details_id && details_other && credit_linear) {//判断是否可以直接贷款
                    loan_details_button.setText(R.string.loan_apply_now);
                }
                String data_id = loanDatailsData.getData_id();
                Pattern pattern = Pattern.compile("\\[(.*)\\]");
                Matcher matcher = pattern.matcher(data_id);
                String ResponseDates = "";
                while (matcher.find()) {
                    ResponseDates = matcher.group(1);
                }
                String[] data_ids = ResponseDates.split(",");
                for (String data_id1 : data_ids) {
                    if (data_id1.contains("1")) {
                        basic_information_linear.setVisibility(View.VISIBLE);
                    }
                    if (data_id1.contains("2")) {
                        phone_operator_linear.setVisibility(View.VISIBLE);
                    }
                    if (data_id1.contains("3")) {
                        esame_credit_linear.setVisibility(View.VISIBLE);
                    }
                    if (data_id1.contains("4")) {
                        etails_id_linear.setVisibility(View.VISIBLE);
                    }
                    if (data_id1.contains("5")) {
                        details_other_linear.setVisibility(View.VISIBLE);
                    }
                }
                //产品详情

                //申请条件
                String loan_tiaojian = loanDatailsData.getTiaojian() + "";
                if (!loan_tiaojian.isEmpty() && !loan_tiaojian.equals("null") && !loan_tiaojian.equals("")) {
                    audit_text.setText(loan_tiaojian);
                }


                //借款审核细节
                String details = loanDatailsData.getDetail() + "";
                if (!details.isEmpty() && !details.equals("null") && !details.equals("")) {
                    details_list.clear();
                    Map<String, String> detailMap = new HashMap<>();
                    JSONObject obj = new JSONObject(details);
                    audit1.setText("贷款类型：" + obj.optString("loan_type"));
                    audit2.setText("面向人群：" + obj.optString("group"));
                    audit3.setText("审核方式：" + obj.optString("check_type"));
                    audit4.setText("到账方式：" + obj.optString("in_account_type"));
                    audit5.setText("实际到账：" + obj.optString("in_account"));
                    audit6.setText("还款途径：" + obj.optString("repayment"));
                    audit7.setText("还款方式：" + obj.optString("repay_type"));
                    audit8.setText("提前还款：" + obj.optString("prepayment"));
                    audit9.setText("逾期还款：" + obj.optString("delinquency"));
                    audit10.setText("能否提额：" + obj.optString("is_up"));
                    audit11.setText("所属平台：" + obj.optString("platform"));
                    //新手引导
                    guid_text.setText(obj.optString("guid") + "");
                }

                OkHttpManager.getAsync(HttpURL.getInstance().USERSTA +
                        "&no=" + AppUtil.getInstance().getChannel(this, 0) +
                        "&ver" + AppUtil.getInstance().getVersionName(1, this), "USERSTA", this);
                break;
            case "SesameCredit":
                JSONObject object = new JSONObject(result);
                JSONObject jsonObject = new JSONObject(object.optString("data"));
                String params = "";
                String sign = "";
                if (jsonObject.has("params")) {
                    params = jsonObject.optString("params");
                }
                if (jsonObject.has("sign")) {
                    sign = jsonObject.optString("sign");
                }
                Map extParams = new HashMap<>();
                creditApp.authenticate(this, "1002755", "", params, sign, extParams, iCreditListener);
                break;
            case "authorize":
                if (result.contains("成功")) {
                    credit_linear = true;
                    oan_details_esame_credit.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
                } else {
                    credit_linear = false;
                    oan_details_esame_credit.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
                }
                break;
            case "comment_list":
                //评论ok
                if (result.contains("成功")) {
                    JSONObject jsonobj = new JSONObject(result);
                    JSONObject jsonobjdata = new JSONObject(jsonobj.optString("data"));
                    JSONArray jsonarr = jsonobjdata.getJSONArray("list");
                    String page_total1 = jsonobjdata.get("page_total") + "";
                    evaluate_totle_page = Integer.parseInt(page_total1);
                    evaluateDataArr = new EvaluateData[jsonarr.length()];
                    if (jsonarr.length() != 0) {
                        EvaluateData evaluateData = null;
                        for (int j = 0; j < jsonarr.length(); j++) {
                            evaluateData = new EvaluateData();
                            JSONObject obj = jsonarr.getJSONObject(j);
                            evaluateData.setComment(obj.getString("comment"));
                            evaluateData.setScore(obj.getString("score"));
                            evaluateData.setType(obj.getString("type"));
                            evaluateData.setCreated_at(obj.getString("created_at"));
                            evaluateData.setMobile(obj.getString("mobile"));
                            evaluateDataList.add(evaluateData);
                            evaluateDataArr[j] = evaluateData;
                        }
//                        evaluateRecyclerAdapter.addAll(evaluateDataArr);
//                        evaluateRecyclerAdapter.notifyDataSetChanged();

                        EvaluateListAdapter.notifyDataSetChanged();
                        listView.loadComplete();
                    }
                }
                break;
            case "loanrecord":
                if (result.contains("成功")) {
//                    ToatUtils.showShort1(this, "成功");
                }
                break;
            case "USERSTA":
                JSONObject jsonObject1 = new JSONObject(result);
                jsonObject1 = new JSONObject(jsonObject1.getString("data"));
                status = jsonObject1.getString("status");
                if (!TextUtils.isEmpty(status) && status.equals("1")) {
                    loan_details_button.setVisibility(View.GONE);
                } else {
                    loan_details_button.setVisibility(View.VISIBLE);
                }
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getHttp();
        getHttpcommentlist();//获取评价
        MobclickAgent.onResume(this);
        product_or_evaluate();
        if (phone_operator && basic_information && details_id && details_other && credit_linear) {
            loan_details_button.setText(R.string.loan_apply_now);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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
//                switch (i) {
//                    case 1:
//                        if (!StringUtil.isNullOrEmpty(s.toString())) {
//                            if (Double.parseDouble(s.toString()) > loanMax || Double.parseDouble(s.toString()) < loanMin) {
//                                ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的金额已经超出范围");
//                                loan_details_editText_range.setText("" + (int) loanMax);
//                            }
////                            else {
////                                if (anInt != 0) {
////                                    double dou = Double.parseDouble(loan_details_editText_day.getText().toString());
////                                    loan_details_repayment.setText(getAlgorithm(Double.parseDouble(s.toString()), dou, profit));
////                                }
////                            }
//                        } else {
//                            loan_details_editText_range.setText("0");
//                        }
//                        break;
//                    case 2:
//                        if (!StringUtil.isNullOrEmpty(s.toString())) {
//                            if (Double.parseDouble(s.toString()) > day_monthMax || Double.parseDouble(s.toString()) < day_monthMin) {
//                                if (loanDatailsData != null && !StringUtil.isNullOrEmpty(loanDatailsData.getFv_unit()) &&
//                                        loanDatailsData.getFv_unit().equals("1")) {
//                                    ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的月份已经超出范围");
//                                    loan_details_editText_day.setText("" + (int) day_monthMax);
//                                } else {
//                                    ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的天数已经超出范围");
//                                    loan_details_editText_day.setText("" + (int) day_monthMax);
//                                }
//                            }
////                            else {
////                                if (anInt != 0) {
////                                    double dou = Double.parseDouble(loan_details_editText_range.getText().toString());
////                                    loan_details_repayment.setText(getAlgorithm(dou, Double.parseDouble(s.toString()), profit));
////                                }
////                            }
//                        } else {
//                            loan_details_editText_day.setText("0");
//                        }
//                        break;
//                }
            }
        };
        return textWatcher;
    }


    private int isMountAndTimeRight() {
        int num = 0;
        if (!StringUtil.isNullOrEmpty(loan_details_editText_range.getText().toString())) {
            if (Double.parseDouble(loan_details_editText_range.getText().toString()) > loanMax || Double.parseDouble(loan_details_editText_range.getText().toString()) < loanMin) {
                ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的金额已经超出范围");
                loan_details_editText_range.setText("" + (int) loanMax);
            } else {
                num = num + 1;
            }
        } else {
            loan_details_editText_range.setText(loanMax + "");
        }

        if (!StringUtil.isNullOrEmpty(loan_details_editText_day.getText().toString())) {
            if (loan_day_type == 0) {

                if (Double.parseDouble(loan_details_editText_day.getText().toString()) > day_monthMax || Double.parseDouble(loan_details_editText_day.getText().toString()) < day_monthMin) {
                    if (loanDatailsData != null && !StringUtil.isNullOrEmpty(loanDatailsData.getFv_unit()) &&
                            loanDatailsData.getFv_unit().equals("1")) {
                        ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的月份已经超出范围");
                        loan_details_editText_day.setText("" + (int) day_monthMax);
                    } else {
                        ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的天数已经超出范围");
                        loan_details_editText_day.setText("" + (int) day_monthMax);
                    }
                } else {
                    num = num + 1;
                }
            } else {
                double inputMount = Double.parseDouble(loan_details_editText_day.getText().toString());
                boolean isRightDate = false;

                for (int i = 0; i < dayArr.length; i++) {
                    if (inputMount == Double.parseDouble(dayArr[i])) {
                        isRightDate = true;
                        break;
                    }
                }

                if (!isRightDate) {
                    ToatUtils.showShort1(LoanDetailsActivity.this, "您输入的时间份已经超出范围");
                    loan_details_editText_day.setText("" + (int) day_monthMax);
                } else {
                    num = num + 1;
                }
            }
        } else {
            loan_details_editText_day.setText(day_monthMax + "");
        }

        return num;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String datastring = "";
        if (requestCode == 1000) {
            datastring = data.getStringExtra("operator");
            if (!TextUtils.isEmpty(datastring) && datastring.equals("1")) {
                phone_operator = true;
                loan_details_phone_operator.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
            } else {
                phone_operator = false;
                loan_details_phone_operator.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
            }
            return;
        } else if (requestCode == 1001) {
            datastring = data.getStringExtra("operator");
            if (!TextUtils.isEmpty(datastring) && datastring.equals("1")) {
                basic_information = true;
                loan_details_basic_information.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
            } else {
                basic_information = false;
                loan_details_basic_information.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
            }
            return;
        } else if (requestCode == 1002) {
            datastring = data.getStringExtra("operator");
            if (!TextUtils.isEmpty(datastring) && datastring.equals("1")) {
                details_id = true;
                loan_details_id.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
            } else {
                details_id = false;
                loan_details_id.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
            }
            return;
        } else if (requestCode == 1003) {
            datastring = data.getStringExtra("operator");
            if (!TextUtils.isEmpty(datastring) && datastring.equals("1")) {
                details_other = true;
                loan_details_other.setBackgroundResource(R.mipmap.ic_loan_details_authentication);
            } else {
                details_other = false;
                loan_details_other.setBackgroundResource(R.mipmap.ic_loan_detail_no_authentication);
            }
            return;
        }
        CreditApp.onActivityResult(requestCode, resultCode, data);
    }

    ICreditListener iCreditListener = new ICreditListener() {
        @Override
        public void onComplete(Bundle result) {
            credit_linear = true;
            Set keys = result.keySet();
            Map<String, Object> map = new HashMap<>();
            for (Object key : keys) {
                map.put(key.toString(), result.getString(key.toString()));
            }
            map.put("uid", SharedPreferencesUtils.get(LoanDetailsActivity.this, "uid", "").toString());
            OkHttpManager.postAsync(HttpURL.getInstance().AUTHORIZE, "authorize", map, LoanDetailsActivity.this);

        }

        @Override
        public void onError(Bundle error) {
            credit_linear = false;
            Set keys = error.keySet();
            Map<String, Object> map = new HashMap<>();
            for (Object key : keys) {
                map.put(key.toString(), error.getString(key.toString()));
            }
            map.put("uid", SharedPreferencesUtils.get(LoanDetailsActivity.this, "uid", "").toString());
            OkHttpManager.postAsync(HttpURL.getInstance().AUTHORIZE, "authorize", map, LoanDetailsActivity.this);
        }

        @Override
        public void onCancel() {
            Log.d("", "");
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
                    view_information.setVisibility(View.GONE);
                    product_details_line.setVisibility(View.VISIBLE);
                } else {
                    view_information.setVisibility(View.VISIBLE);
                    product_details_line.setVisibility(View.GONE);
                }
                evaluate_line_layout.setVisibility(View.GONE);
                view_infor1.setBackgroundColor(Color.parseColor("#78B2EE"));
                view_infor2.setBackgroundColor(Color.parseColor("#a6a6a6"));
                details_information_line.setTextColor(Color.parseColor("#78B2EE"));
                details_evaluate_line.setTextColor(Color.parseColor("#a6a6a6"));
                break;
            case 2:
                product_details_line.setVisibility(View.GONE);
                view_information.setVisibility(View.GONE);
                evaluate_line_layout.setVisibility(View.VISIBLE);
                details_evaluate_line.setTextColor(Color.parseColor("#78B2EE"));
                view_infor2.setBackgroundColor(Color.parseColor("#78B2EE"));
                details_information_line.setTextColor(Color.parseColor("#a6a6a6"));
                view_infor1.setBackgroundColor(Color.parseColor("#a6a6a6"));

                break;
        }
    }

    @Override
    public void onLoad() {
        getHttpcommentlist();

    }
}