package com.jsy.jsydemo.EntityClass;

/**
 * Created by vvguoliang on 2017/7/1.
 * 注册 登入 验证码 修改
 */

public class RegisterSignCodeModify {

    private String info = "";
    private int status = 0;
    private String url = "";
    private Boolean referer = false;
    private String state = "";


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getReferer() {
        return referer;
    }

    public void setReferer(Boolean referer) {
        this.referer = referer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
