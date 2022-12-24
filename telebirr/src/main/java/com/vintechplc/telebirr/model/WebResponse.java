package com.vintechplc.telebirr.model;

import com.google.gson.annotations.SerializedName;
<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TradeWebPay.java

/**
 * Created by wgy on 2017/4/25.
 */

public class TradeWebPay {
========
public class WebResponse {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/model/WebResponse.java


    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String msg;

    @SerializedName("data")
    private ToPayPath data;

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

    public ToPayPath getData() {
        return data;
    }

    public void setData(ToPayPath data) {
        this.data = data;
    }
}
