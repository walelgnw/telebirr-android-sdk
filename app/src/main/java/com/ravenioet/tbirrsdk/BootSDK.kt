package com.ravenioet.tbirrsdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.ravenioet.tbirrsdk.databinding.ActivityBootSdkBinding
import com.vintechplc.telebirr.model.PaymentResult
import com.vintechplc.telebirr.model.TeleBirrPack
import com.vintechplc.telebirr.utils.PayUtil
import com.vintechplc.telebirr.setups.BuildPayRequest

class BootSDK : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBootSdkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBootSdkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            initTeleBirrRemote(TeleBirrPack().local(),"100")
        }
    }

    private fun initTeleBirrRemote(payData: TeleBirrPack?, price: String?) {
        if (payData != null) {
            val request = BuildPayRequest()
            request.appId = payData.appId
            request.setNotifyUrl(payData.notifyUrl)
            request.outTradeNo = payData.outTradeNo
            request.setReceiveName(payData.receiverName)
            request.setReturnUrl(payData.returnUrl)
            request.setShortCode(payData.shortCode)
            request.setSubject(payData.subject)
            request.setTimeoutExpress(payData.timeoutExpress)
            request.setTotalAmount(price)
            if (payData.appKey != null && payData.publicKey != null) {
                PayUtil.getInstance().startPayment(
                    request, this,payData.inAppPaymentUrl,payData.appKey, payData.publicKey
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

}