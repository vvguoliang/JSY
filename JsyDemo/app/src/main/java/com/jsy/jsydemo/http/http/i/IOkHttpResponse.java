package com.jsy.jsydemo.http.http.i;

import java.io.IOException;

import okhttp3.Response;

/**
 * vvguolaing  2017-6-23
 *
 * 返回成功或者失败
 */
public interface IOkHttpResponse {

	void onFailed(Exception exception);

	void onSuccess(Response response) throws IOException;
}
