package com.jsy.jsydemo.EntityClass;

/**
 * Created by jsy_zj on 2017/10/23.
 */

public class LoanBrowsData {

    /**
     * pro_name : 简单借款
     * pro_describe : 资料简单，操作简单5000元到手
     * img : /20170817/511ca80ebc84f03d056349e594a6f8df.jpg
     * created_at : 2017-08-17 18:04:22
     */

    private String pro_name;
    private String pro_describe;
    private String img;
    private String created_at;

    public LoanBrowsData() {
    }

    public LoanBrowsData(String pro_name, String pro_describe, String img, String created_at) {
        this.pro_name = pro_name;
        this.pro_describe = pro_describe;
        this.img = img;
        this.created_at = created_at;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_describe() {
        return pro_describe;
    }

    public void setPro_describe(String pro_describe) {
        this.pro_describe = pro_describe;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
