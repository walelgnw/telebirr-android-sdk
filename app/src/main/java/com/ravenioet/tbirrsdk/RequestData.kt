package com.ravenioet.tbirrsdk

import com.google.gson.annotations.SerializedName

data class RequestData(
    @SerializedName("userId") val userId: String,
    @SerializedName("amount") val amount: String
)
