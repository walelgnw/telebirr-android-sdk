package com.ravenioet.tbirrsdk;

import com.google.gson.annotations.SerializedName;
import com.vintechplc.telebirr.bean.ResponseData;

/**
 * Created by wgy on 2017/4/25.
 */

class TradeWebPayResponse {


    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String msg;

    @SerializedName("data")
//    private DataResponse data;
    private ResponseData data;

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

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }
}
