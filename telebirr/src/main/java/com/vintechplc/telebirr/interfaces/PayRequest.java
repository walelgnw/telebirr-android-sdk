package com.vintechplc.telebirr.interfaces;



import com.vintechplc.telebirr.model.SDKPayRequest;
import com.vintechplc.telebirr.model.WebResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TeleRequest.java
interface TeleRequest {
========
public interface PayRequest {
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/interfaces/PayRequest.java

    //String TEST_HOST = "http://196.188.120.3:10443/service-openup/";
    //String TEST_HOST = "http://196.188.120.3:11443/ammapi/service-openup/";

    //String PROD_HOST = "https://app.ethiomobilemoney.et:2121/ammapi/payment/service-openup/";

    //String HOST = "http://app.ethiomobilemoney.et:2121/";

    @POST("toTradeSDKPay")
<<<<<<<< HEAD:telebirr/src/main/java/com/vintechplc/telebirr/setups/TeleRequest.java
    Observable<TradeWebPay> toTradeWebPay(@Body SDKPayRequest request);
========
    Observable<WebResponse> toTradeWebPay(@Body SDKPayRequest request);
>>>>>>>> winux:telebirr/src/main/java/com/vintechplc/telebirr/interfaces/PayRequest.java

}
