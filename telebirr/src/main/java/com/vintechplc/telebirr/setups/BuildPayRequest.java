package com.vintechplc.telebirr.setups;

import java.util.HashMap;
import java.util.UUID;

/**
 * appId = 'ce83aaa3dedd42ab88bd017ce1ca2dd8',
 * returnUrl = '',
 * notifyUrl = '',
 * subject = '测试商品',
 * outTradeNo = '20210610',
 * timeoutExpress = '30',
 * totalAmount = '30',
 * shortCode = '10011',
 * receiveName = '测试收款',
 * nonce = 'd0f27cab70a6406cba55fd8a5ac2659b',
 * timestamp = '1634195036085',
 * returnApp = 'cn.easier.testswitch'
 */
<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
public class BuildRequest extends HashMap<String, String> {
========
public class BuildPayRequest extends HashMap<String, String> {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
    private static final String APP_ID = "appId";
    private static final String RETURN_URL = "returnUrl";
    private static final String NOTIFY_URL = "notifyUrl";
    private static final String SUBJECT = "subject";
    private static final String OUT_TRADE_NO = "outTradeNo";
    private static final String TIMEOUT_EXPRESS = "timeoutExpress";
    private static final String TOTAL_AMOUNT = "totalAmount";
    private static final String SHORT_CODE = "shortCode";
    private static final String RECEIVE_NAME = "receiveName";
    private static final String NONCE = "nonce";
    private static final String TIMESTAMP = "timestamp";
    private static final String RETURN_APP = "returnApp";

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest() {
========
    public BuildPayRequest() {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        setNonce(UUID.randomUUID().toString().replaceAll("-", ""));
        setTimestamp(String.valueOf(System.currentTimeMillis()));
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setAppId(String v) {
========
    public BuildPayRequest setAppId(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(APP_ID, v);
        return this;
    }

    public String getAppId(){
        return get(APP_ID);
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setNotifyUrl(String v) {
========
    public BuildPayRequest setNotifyUrl(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(NOTIFY_URL, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setOutTradeNo(String v) {
========
    public BuildPayRequest setOutTradeNo(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(OUT_TRADE_NO, v);
        return this;
    }

    public String getOutTradeNo() {
        return get(OUT_TRADE_NO);
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setReceiveName(String v) {
========
    public BuildPayRequest setReceiveName(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(RECEIVE_NAME, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setReturnUrl(String v) {
========
    public BuildPayRequest setReturnUrl(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(RETURN_URL, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setShortCode(String v) {
========
    public BuildPayRequest setShortCode(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(SHORT_CODE, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setSubject(String v) {
========
    public BuildPayRequest setSubject(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(SUBJECT, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setTimeoutExpress(String v) {
========
    public BuildPayRequest setTimeoutExpress(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(TIMEOUT_EXPRESS, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setTotalAmount(String v) {
========
    public BuildPayRequest setTotalAmount(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(TOTAL_AMOUNT, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setNonce(String v) {
========
    public BuildPayRequest setNonce(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(NONCE, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setTimestamp(String v) {
========
    public BuildPayRequest setTimestamp(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(TIMESTAMP, v);
        return this;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildRequest.java
    public BuildRequest setReturnApp(String v) {
========
    public BuildPayRequest setReturnApp(String v) {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/setups/BuildPayRequest.java
        checkValue(RETURN_APP, v);
        return this;
    }

    private void checkValue(String key, String v) {
        if (v == null) {
            remove(key);
        } else {
            put(key, v);
        }
    }

}
