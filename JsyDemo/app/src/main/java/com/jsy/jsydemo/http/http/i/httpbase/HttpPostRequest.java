package com.jsy.jsydemo.http.http.i.httpbase;

import android.content.Context;

import com.jsy.jsydemo.http.http.i.OkHttpCallBack;

import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * vvguoliang 2017-6-23
 * 创建回调获取数据
 */
public class HttpPostRequest extends OKHttpDeserialization {

    public HttpPostRequest(Context context, HttpEntity httpEntity, OkHttpCallBack okHttpCallBack) {
        super(context, httpEntity, okHttpCallBack);
    }

    @Override
    public Request getRequest() {
        return getRequestBuilder().url(getUrl()).post(getRequestBody()).build();
    }

    @Override
    public RequestBody getRequestBody() {
        if (httpEntity.getMap() == null)
            return RequestBody.create(null, "");
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> set = httpEntity.getMap().keySet();
        for (String key : set) {
            builder.add(key, httpEntity.getMap().get(key));
        }
        return builder.build();
    }

    @Override
    public String getUrl() {
        String url = httpEntity.getUrl();
        // auth_id/
//		String mid = SharedPreferencesUtils.get(context, "Mid", "") + "";
//		if (!"".equals(mid) && !url.contains(mid)) {
//			url = url.replace("auth_id/", "auth_id/" + mid + "/"+"source/Android");
//		}
        httpEntity.setUrl(url);
        return httpEntity.getUrl();
    }
}
