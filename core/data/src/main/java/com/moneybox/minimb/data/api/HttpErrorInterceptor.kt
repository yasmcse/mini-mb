package com.moneybox.minimb.data.api

import com.moneybox.minimb.common.AuthSessionManager
import com.moneybox.minimb.common.MoneyBoxNavigator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HttpErrorInterceptor @Inject constructor(
    private val sessionManager: AuthSessionManager,
    private val navigator: MoneyBoxNavigator
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder = request.newBuilder()

        val response = chain.proceed(builder.build())
        if (response.code == 401) {
            sessionManager.clear()
            navigator.navigateToMainActivity()
        }
        response.close()
        return chain.proceed(builder.build())
    }
}