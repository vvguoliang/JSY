package com.jsy.jsydemo.EntityClass;

/**
 * Created by vvguoliang on 2017/7/5.
 * 快速办法数据
 */

public class SpeedLoanData {

    private String property_id = "";
    private String property_type = "";
    private String property_name = "";
    private String money = "";
    private String icon = "";
    private String description = "";

    public SpeedLoanData() {

    }

    public SpeedLoanData(String property_id, String property_type, String property_name, String money, String icon, String description) {
        this.property_id = property_id;
        this.property_name = property_name;
        this.property_type = property_type;
        this.money = money;
        this.icon = icon;
        this.description = description;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getProperty_name() {
        return property_name;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
