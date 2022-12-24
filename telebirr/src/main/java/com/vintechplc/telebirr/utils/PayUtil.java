package com.vintechplc.telebirr.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.vintechplc.telebirr.logs.SessionLogger;
import com.vintechplc.telebirr.model.PaymentResult;
import com.vintechplc.telebirr.interfaces.PayResultListener;
import com.vintechplc.telebirr.setups.BuildPayRequest;
import com.vintechplc.telebirr.model.SDKPayRequest;
import com.vintechplc.telebirr.setups.PayOnWeb;

import org.json.JSONObject;

import java.util.TreeMap;

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TelePayUtil.java
public class TelePayUtil {
========
public class PayUtil {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/utils/PayUtil.java

    private final String TAG = "payUtil";

    public static final String TRADESDKPAY = "tradeSDKPay";

    public static final String OUTTRADENO = "outtradeNO";

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TelePayUtil.java
    private PaymentListener paymentListener;
========
    private PayResultListener paymentResultListener;
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/utils/PayUtil.java

    private Activity webViewActivity;

    public void setWebViewActivity(Activity webViewActivity) {
        this.webViewActivity = webViewActivity;
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TelePayUtil.java
    private static TelePayUtil mInstance;

    private TelePayUtil() {

    }

    public static TelePayUtil getInstance() {
        if (mInstance == null) {
            synchronized (TelePayUtil.class) {
                if (mInstance == null) {
                    mInstance = new TelePayUtil();
========
    private static PayUtil mInstance;

    private PayUtil() {

    }

    public static PayUtil getInstance() {
        if (mInstance == null) {
            synchronized (PayUtil.class) {
                if (mInstance == null) {
                    mInstance = new PayUtil();
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/utils/PayUtil.java
                }
            }
        }
        return mInstance;
    }


    /**
     * 支付跳转
     */

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TelePayUtil.java
    public void startPayment(BuildRequest payMapRequest,
========
    public void startPayment(BuildPayRequest payMapRequest,
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/utils/PayUtil.java
                             Activity context, String host, String appKey, String publicKey) {
        if (payMapRequest == null) {
            SessionLogger.log(TAG, "startPayment tradeWebPayRequest is null");
        }
<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TelePayUtil.java
        Intent intent = new Intent(context, TelePayUi.class);
========
        Intent intent = new Intent(context, PayOnWeb.class);
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/utils/PayUtil.java
        intent.putExtra("host",host);
        SDKPayRequest request = new SDKPayRequest();
        request.setAppid(payMapRequest.getAppId());
//        String sign= EncryptUtils.getInstance().encryptSHA256(tradeWebPayRequest);
//        SessionLogger.log(TAG,"startPayment Appid "+tradeWebPayRequest.getAppId()+" ,sign "+sign);
        request.setSign(EncryptUtils.getInstance().encryptSHA256(payMapRequest,appKey));
        JSONObject jsonObject = EncryptUtils.getInstance().objectToJson(payMapRequest);
        TreeMap<String, String> dataOptMap = EncryptUtils.getInstance().JsonToMap(jsonObject);
        String JsonStr = EncryptUtils.getInstance().objectToJsonString(dataOptMap);
        SessionLogger.log(TAG,"startPayment publicRSAEncrypt "+JsonStr);
        publicKey = publicKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\n", "")
                .trim();
        SessionLogger.log(TAG,"appKey "+appKey);
        SessionLogger.log(TAG,"PublicKey "+publicKey);
        String ussd = null;
        try {
            ussd = EncryptUtils.getInstance().encryptByPublicKey(JsonStr,publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        SessionLogger.log(TAG,"startPayment ussd "+ussd);
        request.setUssd(ussd);
        intent.putExtra(TRADESDKPAY, request);
        intent.putExtra(OUTTRADENO, payMapRequest.getOutTradeNo());
        context.startActivity(intent);
    }

    /**
     * 停止支付
     */
    public void stopPayment() {
        if (webViewActivity != null) {
            webViewActivity.finish();
            webViewActivity = null;
        }
    }

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TelePayUtil.java
    public void setPaymentResultListener(PaymentListener paymentListener) {
        this.paymentListener = paymentListener;
        Log.d(TAG, "result listener init");
========
    public void setPaymentResultListener(PayResultListener paymentResultListener) {
        this.paymentResultListener = paymentResultListener;
        SessionLogger.log(TAG, "result listener init");
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/utils/PayUtil.java
    }

    /**
     * 支付结果回调
     */
<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TelePayUtil.java
    protected void callBackPaymentResult(PaymentResult result) {
        if (paymentListener != null) {
            Log.d(TAG, "result listener found");
            paymentListener.paymentResult(result);
========
    public void callBackPaymentResult(PaymentResult result) {
        if (paymentResultListener != null) {
            SessionLogger.log(TAG, "result listener found");
            paymentResultListener.paymentResult(result);
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/utils/PayUtil.java
        }else {
            SessionLogger.log(TAG, "result listener dead");
        }
    }
}
