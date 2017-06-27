package com.jsy.jsydemo.http.http.i.httpbase;

import android.content.Context;

import com.google.gson.Gson;
import com.jsy.jsydemo.http.http.i.OkHttpCallBack;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import okhttp3.Request;
import okhttp3.RequestBody;

@SuppressWarnings({"MalformedRegex", "StatementWithEmptyBody"})
public abstract class OKHttpDeserialization extends AbstractBaseOkHttp {

    public OKHttpDeserialization(Context context, HttpEntity httpEntity, OkHttpCallBack okHttpCallBack) {
        super(context, httpEntity, okHttpCallBack);
    }

    // { "Key": "M=", "MSG": "0", "COD": "102" }
    @Override
    protected boolean deserialization() {
        if (!httpEntity.isDecode())
            return true;
        try {// 用正则表达式来做截取中间{}中间部分
//            if (!StringUtil.isNullOrEmpty(httpEntity.getResponseDate()) && httpEntity.getResponseDate().contains("{")) {
//                Pattern pattern = Pattern.compile("\\{(.*)\\}");
//                Matcher matcher = pattern.matcher(httpEntity.getResponseDate());
//                String ResponseDates = "";
//                while (matcher.find()) {
//                    ResponseDates = matcher.group(1);
//                }
//                httpEntity.setResponseDate("{" + ResponseDates + "}");
//            }
            Gson gson = new Gson();
            if (httpEntity.getInterfaceLevel() == HttpEntity.LEVEL1) {
//                if (httpEntity.getResponseDate() != null) {
//                    Object t = gson.fromJson(httpEntity.getResponseDate(), httpEntity.getType());
//                    httpEntity.setObject(t);
//                }
            } else if (httpEntity.getInterfaceLevel() == HttpEntity.LEVEL9) {
//                ResponseDate responseDate = gson.fromJson(httpEntity.getResponseDate(), ResponseDate.class);
//                httpEntity.setSxsCode(responseDate.getCOD());
//                httpEntity.setSxsMsg(responseDate.getMSG());
//                httpEntity.setKey(responseDate.getKey());
//                if (httpEntity.getKey() == null || httpEntity.getKey().startsWith("OK")
//                        || httpEntity.getKey().startsWith("Error")) {
//                    httpEntity.setRemindCustomer(true);
//                    return true;
//                }
//                httpEntity.setRemindCustomer(false);
//                String Key = Utils_passwod.main(responseDate.getKey(), GetMid.getHouQi(context));
//                httpEntity.setDecodingKey(Key);
//                if (Key != null && httpEntity.getType() != null) {
//                    Object t = gson.fromJson(Key, httpEntity.getType());
//                    httpEntity.setObject(t);
//                }
            } else if (httpEntity.getInterfaceLevel() == HttpEntity.LEVEL8) {
//                ResponseDate responseDate = gson.fromJson(httpEntity.getResponseDate(), ResponseDate.class);
//                httpEntity.setSxsCode(responseDate.getCOD());
//                httpEntity.setSxsMsg(responseDate.getMSG());
//                httpEntity.setKey(responseDate.getKey());
//                httpEntity.setObject(responseDate);
            } else if (httpEntity.getInterfaceLevel() == HttpEntity.LEVEL10) {
//                ResponseDate responseDate = gson.fromJson(httpEntity.getResponseDate(), ResponseDate.class);
//                httpEntity.setSxsCode(responseDate.getCOD());
//                httpEntity.setSxsMsg(responseDate.getMSG());
//                httpEntity.setKey(responseDate.getKey());
//                if (httpEntity.getKey() == null || httpEntity.getKey().startsWith("OK")
//                        || httpEntity.getKey().startsWith("Error")) {
//                    httpEntity.setRemindCustomer(true);
//                    return true;
//                }
//                httpEntity.setRemindCustomer(false);
//                String Key = Utils_passwod.main(responseDate.getKey(), GetMid.getHouQi(context));
//                httpEntity.setDecodingKey(Key);
//                httpEntity.setObject(responseDate);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取导致崩溃的错误信息
     *
     * @param ex
     * @return 异常内容
     */
    private String formatCrashInfo(Throwable ex) {
        StringBuffer exception = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        printWriter.close();
        String result = writer.toString();
        exception.append(result);
        return exception.toString();
    }

    public abstract Request getRequest();

    public abstract RequestBody getRequestBody();
}
