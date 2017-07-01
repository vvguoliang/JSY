package com.jsy.jsydemo.utils;

import com.jsy.jsydemo.EntityClass.RegisterSignCodeModify;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vvguoliang on 2017/7/1.
 * <p>
 * 公共Json数据解析
 */

public class JsonData {

    /**
     * 单例对象实例
     */
    private static class JsonDataHolder {
        static final JsonData INSTANCE = new JsonData();
    }

    public static JsonData getInstance() {
        return JsonData.JsonDataHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private JsonData() {
    }

    /**
     * readResolve方法应对单例对象被序列化时候
     */
    private Object readResolve() {
        return getInstance();
    }


    public RegisterSignCodeModify getJsonLogoCode(String string) {
        RegisterSignCodeModify registerSignCodeModify = new RegisterSignCodeModify();
        try {
            JSONObject jsonObject = new JSONObject(string);
            registerSignCodeModify.setInfo(jsonObject.optString("info"));
            registerSignCodeModify.setReferer(jsonObject.optBoolean("referer"));
            registerSignCodeModify.setState(jsonObject.optString("state"));
            registerSignCodeModify.setStatus(jsonObject.optInt("status"));
            registerSignCodeModify.setUrl(jsonObject.optString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return registerSignCodeModify;
    }

}
