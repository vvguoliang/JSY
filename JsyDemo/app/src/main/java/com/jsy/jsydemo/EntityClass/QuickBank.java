package com.jsy.jsydemo.EntityClass;

/**
 * Created by vvguoliang on 2017/7/3.
 * 快读办卡 数据
 */

public class QuickBank {

    private String id = "";
    private String name = "";
    private String describe = "";
    private String link = "";
    private String icon = "";
    private String created_at = "";
    private String updated_at = "";

    public QuickBank() {

    }

    public QuickBank(String id, String name, String describe, String link, String icon, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.link = link;
        this.icon = icon;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
