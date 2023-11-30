package com.moneybox.minimb.data.api


import com.moneybox.minimb.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoAuthenticationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader(APP_ID_HEADER_NAME, BuildConfig.APP_ID)
                .addHeader(API_VERSION_HEADER_NAME, BuildConfig.API_VERSION)
                .addHeader(APP_VERSION_HEADER_NAME, BuildConfig.APP_VERSION)
                .addHeader(CONTENT_HEADER_NAME, CONTENT_HEADER_VALUE)
                .build()
        )
    }

    companion object {
        private const val ACCEPT = "Accept"
        private const val APP_ID_HEADER_NAME = "AppId"
        private const val API_VERSION_HEADER_NAME = "ApiVersion"
        private const val APP_VERSION_HEADER_NAME = "AppVersion"
        private const val CONTENT_HEADER_NAME = "ContentDetail-Type"
        private const val CONTENT_HEADER_VALUE = "application/json"
    }
}