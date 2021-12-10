package com.app.emcashmerchant.data.network

import com.app.emcashmerchant.data.model.request.RefreshTokenRequest
import com.app.emcashmerchant.data.model.response.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenRefreshApi {
    @POST("v1/merchants/auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") auth:String,
        @Body refreshTokenRequest: RefreshTokenRequest
    ): RefreshTokenResponse
}