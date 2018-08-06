package com.jsy.jsydemo.EntityClass;

import java.util.List;

/**
 * Created by jsy_zj on 2017/10/23.
 */

public class LoanBrowsDataList {
    private String code = "";
    private String msg = "";
    private List<LoanBrowsData> loanBrowsDatas;

    public LoanBrowsDataList() {
    }

    public LoanBrowsDataList(String code, String msg, List<LoanBrowsData> loanBrowsDatas) {
        this.code = code;
        this.msg = msg;
        this.loanBrowsDatas = loanBrowsDatas;
    }

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

    public List<LoanBrowsData> getLoanBrowsDatas() {
        return loanBrowsDatas;
    }

    public void setLoanBrowsDatas(List<LoanBrowsData> loanBrowsDatas) {
        this.loanBrowsDatas = loanBrowsDatas;
    }
}
