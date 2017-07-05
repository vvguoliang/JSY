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


    public void initUrl(Context context) {
        if (SharedPreferencesUtils.contains(context, HTTP_URL_KEY)) {
            HTTP_URL = SharedPreferencesUtils.get(context, HTTP_URL_KEY, HTTP_URL).toString();
        }
        HTTP_URL = HTTP_URL.replace("\n", "");
        HTTP_URL = HTTP_URL.replace(" ", "");

    }

    /**
     * 更新URL地址
     *
     * @param context
     * @param url
     */
    public void updateUrl(Context context, String url) {
        SharedPreferencesUtils.put(context, HTTP_URL_KEY, url);
    }

    public String CODE = HTTP_URL + "&m=login&a=send_code";

    public String LOGO = HTTP_URL + "&m=login&a=dologin";

    public String PASSWORD = HTTP_URL + "&m=login&a=reset_password";

    public String REGISTER_CODE = HTTP_URL + "&m=register&a=send_code";

    public String REGISTER = HTTP_URL + "&m=register&a=doregiste";

    public String BANNER = HTTP_URL + "&m=banner&a=postList";

    public String HOMEPRODUCT = HTTP_URL + "&m=product&a=change_list";

    public String BANK = HTTP_URL + "&m=bank&a=postList";

    public String PRODUCT = HTTP_URL + "&m=product&a=postList";

    public String  PRODUCT_DETAIL= HTTP_URL + "&m=product&a=postDetail";

}
