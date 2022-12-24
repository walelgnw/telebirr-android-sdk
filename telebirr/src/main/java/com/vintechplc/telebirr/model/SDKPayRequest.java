package com.vintechplc.telebirr.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 功能描述
 *
 * @author liudo
 * @version [V8.5.70.1, 2021/06/16]
 * @since V8.5.70.1
 */
<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/SDKPayRequest.java
class SDKPayRequest implements Serializable {
========
public class SDKPayRequest implements Serializable {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/model/SDKPayRequest.java

    @SerializedName("appid")
    private String appid;

    @SerializedName("sign")
    private String sign;

    @SerializedName("ussd")
    private String ussd;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUssd() {
        return ussd;
    }

    public void setUssd(String ussd) {
        this.ussd = ussd;
    }
}
