package com.jsy.jsydemo.http.http.i.httpbase;

import android.content.Context;

import com.jsy.jsydemo.http.http.i.OkHttpCallBack;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 项目名称：groupBackstage 类描述： 创建时间：2015/10/16 11:15 修改人：Administrator
 * 修改时间：2015/10/16 11:15 修改备注
 */
public class HttpGetRequest extends OKHttpDeserialization {

	public HttpGetRequest(Context context, HttpEntity httpEntity, OkHttpCallBack okHttpCallBack) {
		super(context, httpEntity, okHttpCallBack);
	}

	@Override
	public Request getRequest() {
		return getRequestBuilder().url(getUrlByParams()).build();
	}

	@Override
	public RequestBody getRequestBody() {
		return null;
	}

	/**
	 * 在地址后面加请求参数
	 * 
	 * @return
	 */
	private String getUrlByParams() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getUrl());
		stringBuilder.append("?").append(httpEntity.getParams());
		return stringBuilder.toString();
	}

	@Override
	public String getUrl() {
		return httpEntity.getUrl();
	}
}
