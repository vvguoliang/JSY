package com.jsy.jsydemo.EntityClass;

/**
 * Created by vvguoliang on 2017/9/1.
 */

public class LoanRecordBand {

    private String pro_name = "";
    private String pro_describe = "";
    private String img = "";
    private String created_at = "";

    public LoanRecordBand() {
    }

    public LoanRecordBand(String pro_name, String pro_describe, String img, String created_at) {
        this.pro_describe = pro_describe;
        this.pro_name = pro_name;
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
