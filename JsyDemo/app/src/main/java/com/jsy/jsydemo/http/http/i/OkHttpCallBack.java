package com.jsy.jsydemo.http.http.i;

import com.jsy.jsydemo.http.http.i.httpbase.HttpEntity;

/** vvguoliang  2017-6-23
 *  成功与失败接口
 */
public interface OkHttpCallBack {

    void success(String taskName, String responseDate, HttpEntity httpEntity);

    void onFailure(String taskName, String failure, HttpEntity httpEntity);
}
