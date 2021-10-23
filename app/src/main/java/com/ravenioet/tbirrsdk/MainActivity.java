package com.ravenioet.tbirrsdk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.vintechplc.telebirr.AngolaPayUtil;
import com.vintechplc.telebirr.TradePayMapRequest;
import com.vintechplc.telebirr.bean.PaymentResult;
import com.vintechplc.telebirr.bean.ToPayMsgDTO;
import com.vintechplc.telebirr.util.PaymentResultListener;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private EditText appidet,
    //            receiveCodeet,
    receiverNameet, shortcodeet, subjectet, totalAmountet, notifyUrlet, returnUrlet, outTradeNoet, timeoutExpresset
//            , transactionNoet
            ;

    private String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化三方支付
        AngolaPayUtil.getInstance().setPaymentResultListener(this);
        appidet = findViewById(R.id.appid_et);
        returnUrlet = findViewById(R.id.returnUrl_et);
        notifyUrlet = findViewById(R.id.notifyUrl_et);
//        receiveCodeet = findViewById(R.id.receiveCode_et);
        receiverNameet = findViewById(R.id.receiveName_et);
        shortcodeet = findViewById(R.id.shortCode_et);
        subjectet = findViewById(R.id.subject_et);
        totalAmountet = findViewById(R.id.totalAmount_et);
//        transactionNoet = findViewById(R.id.transactionNo_et);
        outTradeNoet = findViewById(R.id.outTradeNo_et);
        timeoutExpresset = findViewById(R.id.timeoutExpress_et);
        appidet.setText("ce83aaa3dedd42ab88bd017ce1ca2dd8");
//        receiveCodeet.setText("TELEBIRR|BUYGOODS|10011|10|202106210930361406786596851159042|30");
        receiverNameet.setText("Test charge");
        shortcodeet.setText("9000");
        subjectet.setText("Test goods");
        totalAmountet.setText("1");
        timeoutExpresset.setText("30");
//        transactionNoet.setText("202106210930361406786596851159042");
        outTradeNoet.setText("202106210930361406786596851159042");
        notifyUrlet.setText("http://www.baidu.com");
        returnUrlet.setText("http://www.baidu.com");
        //跳转SDK
        findViewById(R.id.sdk).setOnClickListener(View -> {
            if (TextUtils.isEmpty(outTradeNoet.getText())) {
                Toast.makeText(this, "input trade no!", Toast.LENGTH_SHORT).show();
                return;
            }
            TradePayMapRequest request = new TradePayMapRequest()
                    .setAppId(appidet.getText().toString())
                    .setNotifyUrl(notifyUrlet.getText().toString())
                    .setOutTradeNo(outTradeNoet.getText().toString())
                    .setReceiveName(receiverNameet.getText().toString())
                    .setReturnUrl(returnUrlet.getText().toString())
                    .setShortCode(shortcodeet.getText().toString())
                    .setSubject(subjectet.getText().toString())
                    .setTimeoutExpress(timeoutExpresset.getText().toString())
                    .setTotalAmount(totalAmountet.getText().toString());
            //获取参数后跳转三方支付SDK
            AngolaPayUtil.getInstance().startPayment(request, this);
        });
        //跳转APP
        findViewById(R.id.app).setOnClickListener(view -> {
            if (TextUtils.isEmpty(outTradeNoet.getText())) {
                Toast.makeText(this, "input trade no!", Toast.LENGTH_SHORT).show();
                return;
            }

            //请求订单
            TradePayMapRequest request = new TradePayMapRequest()
                    .setAppId(appidet.getText().toString())
                    .setNotifyUrl(notifyUrlet.getText().toString())
                    .setOutTradeNo(outTradeNoet.getText().toString())
                    .setReceiveName(receiverNameet.getText().toString())
//            .setReturnUrl(returnUrlet.getText().toString());
                    .setShortCode(shortcodeet.getText().toString())
                    .setSubject(subjectet.getText().toString())
                    .setTimeoutExpress(timeoutExpresset.getText().toString())
                    .setTotalAmount(totalAmountet.getText().toString())
                    .setReturnApp(BuildConfig.APPLICATION_ID);

            Log.i(TAG, "TradePayRequest:" + request.toString());

            TradeSDKPayRequest mobilerequest = new TradeSDKPayRequest();
            mobilerequest.setAppid(appidet.getText().toString());
            mobilerequest.setSign(EncryptUtils.getInstance().encryptSHA256(request));
            String JsonStr = EncryptUtils.getInstance().objectToJsonString(request);
            String ussd = null;
            try {
                ussd = EncryptUtils.getInstance().encryptByPublicKey(JsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mobilerequest.setUssd(ussd);
            NetWorkManager.getInstance().init();
            NetWorkManager.getInstance().getRequest().toTradeWebPay(mobilerequest).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TradeWebPayResponse>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(TradeWebPayResponse tradeWebPayResponse) {
                    if (null != tradeWebPayResponse.getData()) {
                        //跳转三方支付APP
                        String toPayMsg = tradeWebPayResponse.getData().getToPayMsg();

                        ToPayMsgDTO extrasDTO = new Gson().fromJson(toPayMsg, ToPayMsgDTO.class);
                        Intent intent = getPackageManager().getLaunchIntentForPackage(extrasDTO.getLaunchIntentForPackage());
                        if (intent == null) {
                            Toast.makeText(MainActivity.this, "please install App!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        intent.putExtra("appId", extrasDTO.getExtras().getAppId());
                        intent.putExtra("receiveCode", extrasDTO.getExtras().getReceiveCode());
                        intent.putExtra("receiveName", extrasDTO.getExtras().getReceiveName());
                        intent.putExtra("notifyUrl", extrasDTO.getExtras().getNotifyUrl());
                        intent.putExtra("returnApp", extrasDTO.getExtras().getReturnApp());
                        intent.putExtra("shortCode", extrasDTO.getExtras().getShortCode());
                        intent.putExtra("subject", extrasDTO.getExtras().getSubject());
                        intent.putExtra("totalAmount", extrasDTO.getExtras().getTotalAmount());
                        intent.putExtra("outTradeNo", extrasDTO.getExtras().getOutTradeNo());
                        intent.putExtra("timeoutExpress", extrasDTO.getExtras().getTimeoutExpress());
                        startActivityForResult(intent, 233);
                    } else {
                        Toast.makeText(MainActivity.this, tradeWebPayResponse.getMsg(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });

        });
    }

    /**
     * SDK支付结果回调
     */
    @Override
    public void paymentResult(PaymentResult result) {
        Toast.makeText(this, objectToJsonString(result), Toast.LENGTH_LONG).show();
    }

    private String oldtradeNo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult:");
        if (data != null) {
            Log.i(TAG, "onActivityResult:" + data.getStringExtra("data"));
            Log.i(TAG, "onActivityResult:" + data.getStringExtra("msg"));
            Log.i(TAG, "onActivityResult:" + data.getStringExtra("code"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //App支付结果回调
        String msg = getIntent().getStringExtra("msg");
        int code = getIntent().getIntExtra("code", -1);
        String data = getIntent().getStringExtra("data");
        Object dataobj = JSONToObj(data, AppResult.class);
        if (null != dataobj) {
            String tradeNo = ((AppResult) dataobj).getTradeNo();
            if (!TextUtils.isEmpty(tradeNo) && !tradeNo.equals(oldtradeNo)) {
                oldtradeNo = tradeNo;
            }
            Toast.makeText(this, "code : " + code + ",msg : " + msg + ",data : " + data, Toast.LENGTH_LONG).show();
        }
    }


    <T> Object JSONToObj(String jsonStr, Class<T> obj) {
        T t = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (TextUtils.isEmpty(jsonStr)) {
                return t;
            }
            t = objectMapper.readValue(jsonStr, obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    <T> String objectToJsonString(T obj) {
        if (null == obj) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}