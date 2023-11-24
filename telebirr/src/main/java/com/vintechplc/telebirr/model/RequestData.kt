package com.vintechplc.telebirr.model

import com.google.gson.annotations.SerializedName

data class RequestData(
    @SerializedName("userId") val userId: String,
    @SerializedName("amount") val amount: String
)
