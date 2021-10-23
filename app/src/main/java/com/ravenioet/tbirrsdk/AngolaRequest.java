package com.ravenioet.tbirrsdk;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface AngolaRequest {

    // 填上需要访问的服务器地址
//    String URL_HOST = "http://196.188.120.3:10443/service-openup/";
    // 广州测试环境
//    String URL_HOST = "http://amm.easier.cn/service-openup/";

    // 生产环境
//    String URL_HOST = "https://app.ethiomobilemoney.et:2121/service-openup/";

//    String URL_HOST = "http://app.ethiomobilemoney.et:2121/";
    String URL_HOST = BuildConfig.HOST_URL;

    @POST("toTradeMobielPay")
    Observable<TradeWebPayResponse> toTradeWebPay(@Body TradeSDKPayRequest request);


}
