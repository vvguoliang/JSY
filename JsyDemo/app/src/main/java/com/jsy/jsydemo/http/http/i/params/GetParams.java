package com.jsy.jsydemo.http.http.i.params;

import java.util.concurrent.ConcurrentHashMap;

public class GetParams{

    protected ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();

    /**
     * 添加
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

    /**
     * 添加
     *
     * @param key
     * @param value
     */
    public void put(String key, int value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    /**
     * 添加
     *
     * @param key
     * @param value
     */
    public void put(String key, long value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    /**
     * 移除
     *
     * @param key
     */
    public void remove(String key) {
        urlParams.remove(key);
    }

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    public boolean has(String key) {
        return urlParams.get(key) != null;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }
        return result.toString();
    }

    public GetParams() {
    }
}
