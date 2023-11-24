package com.ravenioet.tbirrsdk

import com.vintechplc.telebirr.model.PayRequest
import com.vintechplc.telebirr.model.TeleBirrPack
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST("init-telebirr")
    fun initTeleBirr(@Body user: RequestData): Call<TeleBirrPack>

    @POST("users")
    fun confirmTeleBirr(@Body user: RequestData): Call<PayRequest>

}