package com.ravenioet.tbirrsdk

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.ravenioet.tbirrsdk.databinding.ActivityBootSdkBinding
import com.vintechplc.telebirr.model.PaymentResult
import com.vintechplc.telebirr.model.TeleBirrPack
import com.vintechplc.telebirr.utils.PayUtil
import com.vintechplc.telebirr.setups.BuildPayRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class BootSDK : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBootSdkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBootSdkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        initTeleBirr("https://fasttimeexpress.net/api/v1/telebirr/")
        binding.fab.setOnClickListener { view ->
            initTeleBirrRemote(TeleBirrPack().fastTime(),"100")
        }
    }

    private fun initTeleBirrRemote(payData: TeleBirrPack?, price: String?) {
        if (payData != null) {
            val request = BuildPayRequest()
            request.appId = payData.appId
            request.setNotifyUrl(payData.notifyUrl)
            request.outTradeNo = payData.outTradeNumber
            request.setReceiveName(payData.receiverName)
            request.setReturnUrl(payData.returnUrl)
            request.setShortCode(payData.shortCode)
            request.setSubject(payData.subject)
            request.setTimeoutExpress(payData.timeoutExpress)
            request.setTotalAmount(price)
            val result = payData.inAppPaymentUrl.substringBeforeLast("/")+'/'
            if (payData.appKey != null && payData.publicKey != null) {
                PayUtil.getInstance().startPayment(
                    request, this,
                    result,
                    payData.appKey,
                    payData.publicKey
                )
            } else {
                val result = PaymentResult()
                result.code = -1
                result.msg = "Payment process failed, Keys not valid"
                paymentResult(result)
            }
        } else {
            val result = PaymentResult()
            result.code = -1
            result.msg = "Payment process failed, Please Try again"
            paymentResult(result)
        }
    }

    fun paymentResult(result: PaymentResult) {
        runOnUiThread(Runnable {

        })
    }

    fun prepareResult( result: PaymentResult) {

    }

    fun confirmPayment(){
        //confirm url to confirm the payment
    }
    fun initTeleBirr(url: String){
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(APIService::class.java)
        val requestData = RequestData("001", amount = "25")
        val call = apiService.initTeleBirr(requestData)

        call.enqueue(object : Callback<TeleBirrPack> {
            override fun onResponse(call: Call<TeleBirrPack>,
                                    response: Response<TeleBirrPack>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    val responseBody = response.body()
                    // Do something with the response body
                    if (responseBody != null) {
                        Log.i("onResponse", "onResponse: ${responseBody.outTradeNumber}")
                        showToast(applicationContext,responseBody.outTradeNumber,Toast.LENGTH_LONG)
                        initTeleBirrRemote(responseBody, responseBody.totalAmount.toString())
                    }
                } else {
                    // Handle error
                    // ...

                    showToast(applicationContext,"OnError 2",Toast.LENGTH_LONG)
                }
            }

            override fun onFailure(call: Call<TeleBirrPack>, t: Throwable) {
                showToast(applicationContext,"OnError",Toast.LENGTH_LONG)
            }
        })
    }
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

}