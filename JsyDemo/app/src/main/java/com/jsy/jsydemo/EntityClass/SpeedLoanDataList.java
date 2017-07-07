package com.jsy.jsydemo.EntityClass;

import java.util.List;

/**
 * Created by vvguoliang on 2017/7/7.
 * 集合数据
 */

public class SpeedLoanDataList {

    private String code = "";
    private String msg = "";
    private List<SpeedLoanData> loanDataList;

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

    public List<SpeedLoanData> getLoanDataList() {
        return loanDataList;
    }

    public void setLoanDataList(List<SpeedLoanData> loanDataList) {
        this.loanDataList = loanDataList;
    }
}
