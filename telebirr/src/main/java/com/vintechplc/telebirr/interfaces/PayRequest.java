package com.vintechplc.telebirr.interfaces;



import com.vintechplc.telebirr.model.SDKPayRequest;
import com.vintechplc.telebirr.model.WebResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PayRequest {

    //String TEST_HOST = "http://196.188.120.3:10443/service-openup/";
    //String TEST_HOST = "http://196.188.120.3:11443/ammapi/service-openup/";

    //String PROD_HOST = "https://app.ethiomobilemoney.et:2121/ammapi/payment/service-openup/";

    //String HOST = "http://app.ethiomobilemoney.et:2121/";

    @POST("toTradeSDKPay")
    Observable<WebResponse> toTradeWebPay(@Body SDKPayRequest request);

}
