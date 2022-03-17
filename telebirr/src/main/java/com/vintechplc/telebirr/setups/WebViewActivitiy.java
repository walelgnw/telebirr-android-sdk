package com.vintechplc.telebirr.setups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.vintechplc.telebirr.R;
import com.vintechplc.telebirr.model.PaymentResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WebViewActivitiy extends Activity {

    private WebView mWebview;

    private final String TAG = "telebirr_pr";

    private String outTradeNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        initWebView();
        initData();
        AngolaPayUtil.getInstance().setWebViewActivity(this);
    }

    @SuppressLint("CheckResult")
    private void initData() {
        Object obj = getIntent().getSerializableExtra(AngolaPayUtil.TRADESDKPAY);
        if (null == obj) {
            Log.e(TAG, "initData tradeSDKPayRequest is null");
            return;
        }
        TradeSDKPayRequest request = (TradeSDKPayRequest) obj;
        Object objNo = getIntent().getSerializableExtra(AngolaPayUtil.OUTTRADENO);
        if (null == objNo) {
            Log.e(TAG, "initData outtradeNO is null");
        }
        outTradeNo = (String) objNo;
        NetWorkManager.getInstance().getRequest().toTradeWebPay(request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TradeWebPayResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                //Log.e(TAG, "Disposable");
            }

            @Override
            public void onNext(@NonNull TradeWebPayResponse tradeWebPayResponse) {
                Log.d(TAG, "toTradeSDKPay success code " + tradeWebPayResponse.getCode() + ", message " + tradeWebPayResponse.getMsg());
                if ("200".equals(tradeWebPayResponse.getCode())) {
                    if (null == tradeWebPayResponse.getData()) {
                        Log.e(TAG, "toTradeSDKPay success data is null ");
                        Toast.makeText(WebViewActivitiy.this, "data error", Toast.LENGTH_LONG).show();
                        return;
                    }
                    mWebview.loadUrl(tradeWebPayResponse.getData().getToPayUrl());
                    mWebview.evaluateJavascript("(function() { return document.getElementsByTagName('html')[0].innerHTML; })();",
                            html -> {
                                Log.d("data", html);
                            });
                } else {
                    Log.e(TAG, "toTradeSDKPay fail ");
                    Toast.makeText(WebViewActivitiy.this, tradeWebPayResponse.getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.e(TAG, "toTradeSDKPay " + throwable.getMessage());
                PaymentResult result;
                    result = new PaymentResult();
                    result.setCode(-10);
                    result.setMsg("Network Error");
                AngolaPayUtil.getInstance().callBackPaymentResult(result);
                Toast.makeText(WebViewActivitiy.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onComplete() {
                //Log.d(TAG, "Complete");
            }
        });
    }

    private void initWebView() {
        NetWorkManager.getInstance().init();
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
                AngolaPayUtil.getInstance().callBackPaymentResult(result);
            } else {
                result = (PaymentResult) obj;
                if (result.getData() != null) {
                    result.getData().setOutTradeNo(outTradeNo);
                }
                if (result.getCode() == 0) {
                    Log.d(TAG, "result code " + result.getCode());
                    AngolaPayUtil.getInstance().callBackPaymentResult(result);
                }
            }
//            AngolaPayUtil.getInstance().callBackPaymentResult(result);
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
        AngolaPayUtil.getInstance().callBackPaymentResult(result);
        AngolaPayUtil.getInstance().stopPayment();
        finish();
    }

}
