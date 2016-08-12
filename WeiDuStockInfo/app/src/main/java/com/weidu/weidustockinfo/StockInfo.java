package com.weidu.weidustockinfo;

/**
 * Created by adimv on 2016/8/12.
 */
public class StockInfo {
    private String company = "";
    private String exchange = "";
    private String closing_price = "";
    private String change = "";
    private String date = "";
    public static final String KEY_CLOSING_PRICE = "l";
    public static final String KEY_CHANGE = "c";
    public static final String KEY_DATE = "lt";
    public static final String KEY_COMPANY = "t";
    public static final String KEY_EXCHANGE = "e";

    public String getClosing_price() {
        return closing_price;
    }

    public void setClosing_price(String closing_price) {
        this.closing_price = closing_price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}

