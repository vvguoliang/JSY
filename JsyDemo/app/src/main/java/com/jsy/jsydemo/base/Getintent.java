package com.jsy.jsydemo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


/**
 * vvguoliang  2017-6-23
 * Getintent 用单例模式 定义公共请求 跳转和SharedPreferences 数据获取方面的信息
 */
@SuppressWarnings("EqualsBetweenInconvertibleTypes")
public class Getintent {

    private static Getintent instance = null;  //单例模式

    public static Getintent getInstance() {
        if (instance == null) {
            synchronized (Getintent.class) {
                if (instance == null) {
                    instance = new Getintent();
                }
            }
        }
        return instance;
    }

    /**
     * @param context 上下文
     * @param c       跳转类
     * @param str     跳转的时候添加数组key值
     * @param obj     跳转的时候添加String形式的obj
     */
    public void getIntent(Activity context, Class c, int resqust, String[] str, Object... obj) {
        if (context != null) {
            Intent intent = new Intent(context, c);
            if (str != null && str.length > 0 && !str.equals(""))
                for (int i = 0; i < str.length; i++) {
                    if (obj[i] instanceof Integer) {
                        intent.putExtra(str[i], (Integer) obj[i]);
                    } else if (obj[i] instanceof String) {
                        intent.putExtra(str[i], (String) obj[i]);
                    } else if (obj[i] instanceof Boolean) {
                        intent.putExtra(str[i], (Boolean) obj[i]);
                    }

                }
            if (resqust == 0)
                context.startActivity(intent);
            else if (resqust == 214748364)
                context.startService(intent);
            else
                context.startActivityForResult(intent, resqust);
        }
    }

    /**
     * @param context  上下文
     * @param name     传过来的String 名字用来到设置任务名
     * @param setCach  是否有缓存
     * @param callback 请求OKhttp
     * @param url      请求URL地址
     * @param c        实体类名
     * @param str      拼接的key
     * @param string   拼接的String类型数据
     */
//    public void httpEntityPost(Context context, String name, int Level, String setCach, OkHttpCallBack callback, String url, Class c, String[] str,
//    String...string)
//    {
//        if (context != null) {
//            HttpEntity httpEntity;
//            String mobile = SharedPreferencesUtils.get(context, "moblie", "") + "";
//            if ("".equals(mobile)) {
//                httpEntity = HttpEntity.getPostEntity(url + get(context, "Mid", "") + "/src/android" + "/sys_version/" +
//                        Build.VERSION.RELEASE + "/source/android/?version=" + AppUtils.getVersionName(1, context));
//            } else {
//                httpEntity = HttpEntity.getPostEntity(url + get(context, "Mid", "") + "/src/android" + "/sys_version/" +
//                        Build.VERSION.RELEASE + "/source/android/mobile/" + mobile + "/?version=" + AppUtils.getVersionName(1, context));
//            }
//
//            String[] strings;
//            String[] strings1;
//            if (str != null && str.length > 0 && !str.equals("")) {
//                strings = new String[str.length + 5];
//                strings1 = new String[str.length + 5];
//                for (int i = 0; i < str.length; i++) {
//                    strings[i] = str[i];
//                    strings1[i] = string[i];
//                }
//                strings[str.length + 1] = "src";
//                strings1[str.length + 1] = "android";
//                strings[str.length + 2] = "sys_version";
//                strings1[str.length + 2] = Build.VERSION.RELEASE + "";
//                strings[str.length + 3] = "source";
//                strings1[str.length + 3] = "android";
//                strings[str.length + 4] = "version";
//                strings1[str.length + 4] = AppUtils.getVersionName(1, context) + "";
//            } else {
//                strings = new String[4];
//                strings1 = new String[4];
//                strings[0] = "src";
//                strings1[0] = "android";
//                strings[1] = "sys_version";
//                strings1[1] = Build.VERSION.RELEASE + "";
//                strings[2] = "source";
//                strings1[2] = "android";
//                strings[3] = "version";
//                strings1[3] = AppUtils.getVersionName(1, context) + "";
//            }
//            for (int i = 0; i < strings.length; i++) {
//                httpEntity.postParams(strings[i], strings1[i]);
//            }
//            if (c != null)
//                httpEntity.setType(c);
//
//            if (!"".equals(setCach))
//                httpEntity.setCach(true, setCach);
//            else
//                httpEntity.setCach(false, setCach);
//            if (Level != 0)
//                httpEntity.setInterfaceLevel(Level, true);
//            httpEntity.build(context, callback, name);
//        }
//}
    }
