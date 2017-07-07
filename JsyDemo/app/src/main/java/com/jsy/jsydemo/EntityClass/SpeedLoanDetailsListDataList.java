package com.jsy.jsydemo.EntityClass;

import java.util.List;

/**
 * Created by vvguoliang on 2017/7/8.
 * 集合数据
 */

public class SpeedLoanDetailsListDataList {

    private String code = "";
    private String msg = "";
    private List<SpeedLoanDetailsListData> loanDetailsListData;

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

    public List<SpeedLoanDetailsListData> getLoanDetailsListData() {
        return loanDetailsListData;
    }

    public void setLoanDetailsListData(List<SpeedLoanDetailsListData> loanDetailsListData) {
        this.loanDetailsListData = loanDetailsListData;
    }
}
