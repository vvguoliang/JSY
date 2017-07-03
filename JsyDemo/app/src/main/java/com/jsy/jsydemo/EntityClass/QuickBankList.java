package com.jsy.jsydemo.EntityClass;


import java.util.List;

/**
 * Created by vvguoliang on 2017/7/3.
 *
 * 办卡数据集合
 */

public class QuickBankList {

    private String code = "";
    private String msg = "";
    private List<QuickBank> quickBankList = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<QuickBank> getQuickBankList() {
        return quickBankList;
    }

    public void setQuickBankList(List<QuickBank> quickBankList) {
        this.quickBankList = quickBankList;
    }
}
