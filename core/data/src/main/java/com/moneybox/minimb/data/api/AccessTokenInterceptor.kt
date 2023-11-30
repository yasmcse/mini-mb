package com.moneybox.minimb.data.api

import com.moneybox.minimb.common.AuthSessionManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val sessionManager: AuthSessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder = request.newBuilder()

        when {
            request.isAuthenticationRequired() -> builder.addHeader(
                "Authorization",
                "Bearer ${sessionManager.accessToken}"
            ).build()
        }
        return chain.proceed(builder.build())
    }
}