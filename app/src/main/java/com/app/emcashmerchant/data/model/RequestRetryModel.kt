package com.app.emcashmerchant.data.model

import okhttp3.Response

data class RequestRetryModel(
    val response: Response?,
    var retryCount:Int = 0
)
