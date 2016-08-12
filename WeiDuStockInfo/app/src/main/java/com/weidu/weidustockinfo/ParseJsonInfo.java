package com.weidu.weidustockinfo;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by adimv on 2016/8/12.
 */
public class ParseJsonInfo {
    String TAG = "ParseJsonInfo";
    StockInfo stockInfo = null;
    public ParseJsonInfo() {
    }
    public StockInfo getStockInfo() {
        return stockInfo;
    }
    public StockInfo decodeMessage(String message) {
        try {
            Log.d(TAG, "Parsing: " + message);
            stockInfo = new StockInfo();
//The json parsing will go here
            JSONObject jObject;
            jObject = new JSONObject(message);
            stockInfo.setCompany(jObject.getString(StockInfo.KEY_COMPANY));
            stockInfo.setExchange(jObject.getString(StockInfo.KEY_EXCHANGE));
            stockInfo.setChange(jObject.getString(StockInfo.KEY_CHANGE));
            stockInfo.setClosing_price(jObject.getString(StockInfo.KEY_CLOSING_PRICE));
            stockInfo.setDate(jObject.getString(StockInfo.KEY_DATE));
            return stockInfo;
        } catch (Exception e) {
            Log.e(TAG, "decodeMessage: exception during parsing");
            e.printStackTrace();
            return null;
        }
    }
}
