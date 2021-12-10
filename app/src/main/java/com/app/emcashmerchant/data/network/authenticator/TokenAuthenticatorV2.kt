package com.app.emcashmerchant.data.network.authenticator

import android.content.Context

import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.model.request.RefreshTokenRequest
import com.app.emcashmerchant.data.model.response.RefreshTokenResponse
import com.app.emcashmerchant.data.network.*
import kotlinx.coroutines.runBlocking
import okhttp3.*


class TokenAuthenticatorV2(
    context: Context,
    private val tokenApi: TokenRefreshApi
) : Authenticator ,SafeApiCall{

    private val appContext = context.applicationContext
    private val userPreferences = SessionStorage(appContext)

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val tokenResponse = getUpdatedToken()) {
                is Resource.Success -> {
                    userPreferences.refreshToken = tokenResponse.value.data.tokens.refreshToken
                    userPreferences.accesToken = tokenResponse.value.data.tokens.accessToken
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${tokenResponse.value.data.tokens.accessToken}")
                        .build()
                }
                else ->{
                    userPreferences.logoutUser()
                    null
                }
            }
        }
    }

    private suspend fun getUpdatedToken(): Resource<RefreshTokenResponse> {
        val refreshTokenRequest = RefreshTokenRequest(userPreferences.refreshToken.toString())
        return safeApiCall {
            tokenApi.refreshToken("Bearer ${userPreferences.accesToken}", refreshTokenRequest)
        }

    }

}