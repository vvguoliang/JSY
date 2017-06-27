package com.jsy.jsydemo.http.http.i;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public interface IOkHttpRequest {
    /**
     * 获取HttpClient
     * @return
     */
    OkHttpClient getOkHttpClient();
    String getUrl();
    /**
     * 配置请求
     * @return
     */
    Request getRequest();

    /**
     * 配置请求
     * @return
     */
    Request.Builder getRequestBuilder();

    /**
     * 处理返回消息
     * @return
     */
    Callback getResponseCallBack();

    /**
     * 获取请求主体
     * @return
     */
    RequestBody getRequestBody();

    /**
     * 启动网络请求
     * @param okHttpClient
     * @param request
     * @param callback
     */
    void execute(OkHttpClient okHttpClient, Request request, Callback callback);
}
