package com.vintechplc.telebirr.setups;

import com.vintechplc.telebirr.interfaces.PayRequest;
import com.vintechplc.telebirr.logs.SessionLogger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class NetWorkManager {
    private static volatile NetWorkManager mInstance;
    private Retrofit retrofit;
    private static volatile PayRequest request = null;

    public static NetWorkManager getInstance() {
        SessionLogger.log("NetWorkManager","init");
        if (mInstance == null) {
            SessionLogger.log("NetWorkManager","creating instance");
            synchronized (NetWorkManager.class) {
                    mInstance = new NetWorkManager();
            }
        }else {
            SessionLogger.log("NetWorkManager","instance found");
        }
        return mInstance;
    }

    public void init(String host) {
        SessionLogger.log("NetWorkManager","init host: "+host);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder builder = request.newBuilder();
                    Request req = builder.addHeader("Content-Type",
                            "application/json").build();
                    return chain.proceed(req);
                }).build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public PayRequest getRequest() {
        SessionLogger.log("NetWorkManager","PayRequest init");
        if (request == null) {
            synchronized (NetWorkManager.class) {
                request = retrofit.create(PayRequest.class);
            }
        }
        return request;
    }
}
