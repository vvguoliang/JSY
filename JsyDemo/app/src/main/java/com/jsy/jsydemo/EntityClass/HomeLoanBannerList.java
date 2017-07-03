package com.jsy.jsydemo.EntityClass;

import java.util.List;

/**
 * Created by vvguoliang on 2017/7/3.
 *
 * 封装 Banner 数据
 */

public class HomeLoanBannerList {

    private String code = "";
    private String msg = "";
    private List<HomeLoanBanner> loanBanners = null;

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

    public List<HomeLoanBanner> getLoanBanners() {
        return loanBanners;
    }

    public void setLoanBanners(List<HomeLoanBanner> loanBanners) {
        this.loanBanners = loanBanners;
    }
}
