package com.vintechplc.telebirr.setups;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.vintechplc.telebirr.model.PaymentResult;

import org.json.JSONObject;

import java.util.TreeMap;

public class TelePayUtil {

    private final String TAG = "telebirr_";

    public static final String TRADESDKPAY = "tradeSDKPay";

    public static final String OUTTRADENO = "outtradeNO";

    private PaymentListener paymentListener;

    private Activity webViewActivity;

    protected void setWebViewActivity(Activity webViewActivity) {
        this.webViewActivity = webViewActivity;
    }

    private static TelePayUtil mInstance;

    private TelePayUtil() {

    }

    public static TelePayUtil getInstance() {
        if (mInstance == null) {
            synchronized (TelePayUtil.class) {
                if (mInstance == null) {
                    mInstance = new TelePayUtil();
                }
            }
        }
        return mInstance;
    }


    /**
     * 支付跳转
     */

    public void startPayment(BuildRequest payMapRequest,
                             Activity context, String host, String appKey, String publicKey) {
        if (payMapRequest == null) {
            Log.e(TAG, "startPayment tradeWebPayRequest is null");
        }
        Intent intent = new Intent(context, TelePayUi.class);
        intent.putExtra("host",host);
        SDKPayRequest request = new SDKPayRequest();
        request.setAppid(payMapRequest.getAppId());
//        String sign= EncryptUtils.getInstance().encryptSHA256(tradeWebPayRequest);
//        Log.d(TAG,"startPayment Appid "+tradeWebPayRequest.getAppId()+" ,sign "+sign);
        request.setSign(EncryptUtils.getInstance().encryptSHA256(payMapRequest,appKey));
        JSONObject jsonObject = EncryptUtils.getInstance().objectToJson(payMapRequest);
        TreeMap<String, String> dataOptMap = EncryptUtils.getInstance().JsonToMap(jsonObject);
        String JsonStr = EncryptUtils.getInstance().objectToJsonString(dataOptMap);
        Log.d(TAG,"startPayment publicRSAEncrypt "+JsonStr);
        publicKey = publicKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\n", "")
                .trim();
        Log.d(TAG,"appKey "+appKey);
        Log.d(TAG,"PublicKey "+publicKey);
        String ussd = null;
        try {
            ussd = EncryptUtils.getInstance().encryptByPublicKey(JsonStr,publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d(TAG,"startPayment ussd "+ussd);
        request.setUssd(ussd);
        intent.putExtra(TRADESDKPAY, request);
        intent.putExtra(OUTTRADENO, payMapRequest.getOutTradeNo());
        context.startActivity(intent);
    }

    /**
     * 停止支付
     */
    protected void stopPayment() {
        if (webViewActivity != null) {
            webViewActivity.finish();
            webViewActivity = null;
        }
    }

    public void setPaymentResultListener(PaymentListener paymentListener) {
        this.paymentListener = paymentListener;
        Log.d(TAG, "result listener init");
    }

    /**
     * 支付结果回调
     */
    protected void callBackPaymentResult(PaymentResult result) {
        if (paymentListener != null) {
            Log.d(TAG, "result listener found");
            paymentListener.paymentResult(result);
        }else {
            Log.d(TAG, "result listener dead");
        }
    }
}
