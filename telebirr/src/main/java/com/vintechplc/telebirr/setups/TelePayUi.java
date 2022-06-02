package com.vintechplc.telebirr.setups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.vintechplc.telebirr.R;
import com.vintechplc.telebirr.model.PaymentResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TelePayUi extends Activity {

    private WebView mWebview;

    private final String TAG = "telebirr_pr";
    public static String host = "";
    private String outTradeNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        Intent intent = getIntent();

        if(intent.hasExtra("host")){
            host = intent.getStringExtra("host");
            initWebView(host);
            initData();
            TelePayUtil.getInstance().setWebViewActivity(this);
        }else {
            PaymentResult result;
            result = new PaymentResult();
            result.setCode(-10);
            result.setMsg("Unable to identify host address");
            TelePayUtil.getInstance().callBackPaymentResult(result);
            finish();
        }
    }

    @SuppressLint("CheckResult")
    private void initData() {
        Object obj = getIntent().getSerializableExtra(TelePayUtil.TRADESDKPAY);
        if (null == obj) {
            Log.e(TAG, "initData tradeSDKPayRequest is null");
            return;
        }
        SDKPayRequest request = (SDKPayRequest) obj;
        Object objNo = getIntent().getSerializableExtra(TelePayUtil.OUTTRADENO);
        if (null == objNo) {
            Log.e(TAG, "initData outtradeNO is null");
        }
        outTradeNo = (String) objNo;
        NetWorkManager.getInstance().getRequest().toTradeWebPay(request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TradeWebPay>() {
            @Override
            public void onSubscribe(Disposable d) {
                //Log.e(TAG, "Disposable");
            }

            @Override
            public void onNext(@NonNull TradeWebPay tradeWebPay) {
                Log.d(TAG, "toTradeSDKPay success code " + tradeWebPay.getCode() + ", message " + tradeWebPay.getMsg());
                if ("200".equals(tradeWebPay.getCode())) {
                    if (null == tradeWebPay.getData()) {
                        Log.e(TAG, "toTradeSDKPay success data is null ");
                         return;
                    }
                    mWebview.loadUrl(tradeWebPay.getData().getToPayUrl());
                    mWebview.evaluateJavascript("(function() { return document.getElementsByTagName('html')[0].innerHTML; })();",
                            html -> {
                                Log.d("data", html);
                            });
                } else {
                    PaymentResult result;
                    result = new PaymentResult();
                    result.setCode(-10);
                    result.setMsg(tradeWebPay.getMsg());
                    TelePayUtil.getInstance().callBackPaymentResult(result);
                    finish();
                 }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.e(TAG, "toTradeSDKPay " + throwable.getMessage());
                PaymentResult result;
                    result = new PaymentResult();
                    result.setCode(-10);
                    result.setMsg("Network Error");
                TelePayUtil.getInstance().callBackPaymentResult(result);
                finish();
            }

            @Override
            public void onComplete() {
                //Log.d(TAG, "Complete");
            }
        });
    }

    private void initWebView(String host) {
        Log.e(TAG, "initData tradeSDKPayRequest host-> "+host);
        NetWorkManager.getInstance().init(host);
        mWebview = findViewById(R.id.webView);
        //mWebview.setWebViewClient(new XWebViewClient());
        mWebview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = mWebview.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);

        webSettings.setAllowContentAccess(false);

        webSettings.setGeolocationEnabled(false);

        webSettings.setAllowFileAccess(false);

        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        mWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mWebview.addJavascriptInterface(new javascriptCallback(), "android");

    }
    private class XWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebview.evaluateJavascript("(function() { return document.getElementsByTagName('html')[0].innerHTML; })();",
                    html -> {
                        Log.d("data", html);
                    });
        }
    }
    public class javascriptCallback {
        @JavascriptInterface
        public void paymentResult(String jsonstr) {
            Log.d(TAG, "paymentResult " + jsonstr);
            Object obj = EncryptUtils.getInstance().JSONToObj(jsonstr, PaymentResult.class);
            PaymentResult result;
            if (obj == null) {
                result = new PaymentResult();
                result.setCode(PaymentResult.SERVER_ERROR);
                result.setMsg("server error");
                TelePayUtil.getInstance().callBackPaymentResult(result);
            } else {
                result = (PaymentResult) obj;
                if (result.getData() != null) {
                    result.getData().setOutTradeNo(outTradeNo);
                }
                if (result.getCode() == 0) {
                    Log.d(TAG, "result code " + result.getCode());
                    TelePayUtil.getInstance().callBackPaymentResult(result);
                }
            }
//            TelePayUtil.getInstance().callBackPaymentResult(result);
            finish();
        }

    }

    boolean isPaymentResultSubmited = false;
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PaymentResult result = new PaymentResult();
        result.setCode(-3);
        result.setMsg("Payment Cancelled");
        TelePayUtil.getInstance().callBackPaymentResult(result);
        TelePayUtil.getInstance().stopPayment();
        finish();
    }

}
