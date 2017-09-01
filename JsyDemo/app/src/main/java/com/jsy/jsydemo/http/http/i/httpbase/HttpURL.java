package com.jsy.jsydemo.http.http.i.httpbase;

import android.content.Context;

import com.jsy.jsydemo.utils.SharedPreferencesUtils;

import java.io.Serializable;

/**
 * Created by vvguoliang on 2017/7/1.
 * <p>
 * OKhttp请求
 */

public class HttpURL implements Serializable {

    /**
     * 单例对象实例
     */
    private static class HttpURLHolder {
        static final HttpURL INSTANCE = new HttpURL();
    }

    public static HttpURL getInstance() {
        return HttpURL.HttpURLHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private HttpURL() {
    }

    /**
     * readResolve方法应对单例对象被序列化时候
     */
    private Object readResolve() {
        return getInstance();
    }

    private String HTTP_URL_KEY = "httpUrlKey";
    private String HTTP_URL = "http://app.jishiyu11.cn/index.php?g=app";

    public String HTTP_URL_PATH = "http://app.jishiyu11.cn/data/upload";

    public String HTTP_OPERATOR = "http://api.tanzhishuju.com/api/gateway";

    public String HTTP_URL_JUAICHA = "http://www.kuaicha.info/mobile/credit/credit.html";

    public String HTTP_DETAILS = "https://www.jishiyu007.com/app/zhiqinggaozhi_bj.html";

    private String HTTP_PATH = "http://47.93.122.140:8001//index.php?g=app";

    public void initUrl(Context context) {
        if (SharedPreferencesUtils.contains( context, HTTP_URL_KEY )) {
            HTTP_URL = SharedPreferencesUtils.get( context, HTTP_URL_KEY, HTTP_URL ).toString();
        }
        HTTP_URL = HTTP_URL.replace( "\n", "" );
        HTTP_URL = HTTP_URL.replace( " ", "" );

    }

    /**
     * 更新URL地址
     *
     * @param context
     * @param url
     */
    public void updateUrl(Context context, String url) {
        SharedPreferencesUtils.put( context, HTTP_URL_KEY, url );
    }

    public String CODE = HTTP_URL + "&m=login&a=send_code";

    public String LOGO = HTTP_URL + "&m=login&a=dologin";

    public String PASSWORD = HTTP_URL + "&m=register&a=reset_password";

    public String REGISTER_CODE = HTTP_URL + "&m=register&a=send_code";

    public String REGISTER = HTTP_URL + "&m=register&a=doregister";

    public String BANNER = HTTP_URL + "&m=banner&a=postList";

    public String HOMEPRODUCT = HTTP_URL + "&m=product&a=change_list";

    public String BANK = HTTP_URL + "&m=bank&a=postList";

    public String PRODUCT = HTTP_URL + "&m=product&a=postList";

    public String PRODUCT_DETAIL = HTTP_URL + "&m=product&a=postDetail";

    public String PRODUCT_FILTER = HTTP_URL + "&m=product&a=filter";

    public String SESAMECREDIT = HTTP_URL + "&m=alipay&a=signEncrypt";

    public String PRODUCTTYPE = HTTP_URL + "&m=product&a=product_type";

    public String USERINFO = HTTP_URL + "&m=userinfo&a=postDetail";

    public String FEEDBACK = HTTP_PATH + "&m=feedback&a=postAdd";

    public String USERINFOADD = HTTP_URL + "&m=userinfo&a=postAdd";

    public String PERSONALDATACREDIT = HTTP_URL + "&m=userdetail&a=credit_list";

    public String PERSONALDATACREDITADD = HTTP_URL + "&m=userdetail&a=credit_add";

    public String COMPANYSTATUSADD = HTTP_URL + "&m=userdetail&a=company_status_add";

    public String COMPANYSTATUSLLIST = HTTP_URL + "&m=userdetail&a=company_status_list";

    public String FAMILYADD = HTTP_URL + "&m=userdetail&a=family_add";

    public String FAMILYLIST = HTTP_URL + "&m=userdetail&a=family_list";

    public String OTHERADD = HTTP_URL + "&m=userdetail&a=other_add";

    public String OTHERLIST = HTTP_URL + "&m=userdetail&a=other_list";

    public String HOUSEADD = HTTP_URL + "&m=userdetail&a=house_add";

    public String HOUSELIST = HTTP_URL + "&m=userdetail&a=house_list";

    public String CARADD = HTTP_URL + "&m=userdetail&a=car_add";

    public String CARLIST = HTTP_URL + "&m=userdetail&a=car_list";

    public String PARPERSADD = HTTP_PATH + "&m=userdetail&a=parpers_add";

    public String PARPERSLIST = HTTP_URL + "&m=userdetail&a=parpers_list";

    public String STATUS = HTTP_URL + "&m=userinfo&a=status";

    public String IDCARDADD = HTTP_PATH + "&m=userdetail&a=idcard_add";

    public String SHARE = HTTP_URL + "&m=userinfo&a=share";

    public String AUTHORIZE = HTTP_URL + "&m=alipay&a=anyAuthorize";

    public String SIGN = HTTP_URL + "&m=userdetail&a=mobileSign";

    public String BASEADD = HTTP_URL + "&m=userdetail&a=base_add";

    public String OTHER_INFO = HTTP_URL + "&m=userdetail&a=other_info_add";

    public String PRODUCTINDEX = HTTP_URL + "&m=product&a=index";

    public String USERDATAIL = HTTP_URL + "&m=userdetail&a=isAuth";

    public String USERDATAILAUTH = HTTP_URL + "&m=userdetail&a=mobile_auth";

    public String ACTIVITY = HTTP_URL + "&m=toutiao&a=activate";

    public String BOOTAPP = HTTP_URL + "&m=app&a=boot";

    public String UPDATE = HTTP_URL + "&m=app&a=update";

    public String HITSPRODUCT = HTTP_URL + "&m=product&a=hits";

    public String REGISTERCODE = HTTP_URL + "&m=register&a=bycode";

    public String USERDETAILBASE = HTTP_URL + "&m=userdetail&a=base_list";

    public String USERDETAILOTHER = HTTP_URL + "&m=userdetail&a=other_info_list";

    public String USERDETAILIDCARD = HTTP_URL + "&m=userdetail&a=idcard_list";

    public String PRODUCTCATELIST = HTTP_URL + "&m=productcate&a=getList";
    /* 评论 拉去*/
    public String COMMENTLIST = HTTP_URL + "&m=comment&a=getList";

    /* 借款记录 */
    public String USERINFORECORD = HTTP_URL + "&m=userinfo&a=record";
}
