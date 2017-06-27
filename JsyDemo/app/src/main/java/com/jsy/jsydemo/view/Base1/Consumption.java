package com.jsy.jsydemo.view.Base1;

/**
 * Created by linlongxin on 2016/1/26.
 */
public class Consumption {

    private String xm;
    private String sj;
    private String lx;
    private float je;
    private float ye;
    private String sh;

    private String pinyin;

    private String shenfen;

    private String url;

    private String titlie;

    private String urltatile;

    public Consumption(String xm, String sj, String lx, float je, float ye, String sh, String pinyin, String shenfen, String url, String titlie, String urltatile) {
        this.xm = xm;
        this.sj = sj;
        this.lx = lx;
        this.je = je;
        this.ye = ye;
        this.sh = sh;
        this.pinyin = pinyin;
        this.shenfen = shenfen;
        this.url = url;
        this.titlie = titlie;
        this.urltatile = urltatile;
    }

    public String getSj() {
        return sj;
    }

    public String getLx() {
        return lx;
    }

    public float getJe() {
        return je;
    }

    public float getYe() {
        return ye;
    }

    public String getSh() {
        return sh;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getShenfen() {
        return shenfen;
    }

    public String getUrl() {
        return url;
    }

    public String getTitlie() {
        return titlie;
    }

    public String getUrltatile() {
        return urltatile;
    }
}
