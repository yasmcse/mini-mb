package com.moneybox.minimb.data.di


import com.moneybox.minimb.data.BuildConfig
import com.moneybox.minimb.data.api.AccessTokenInterceptor
import com.moneybox.minimb.data.api.HttpErrorInterceptor
import com.moneybox.minimb.data.api.MoneyBoxApiService
import com.moneybox.minimb.data.api.NoAuthenticationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(
        noAuthenticationInterceptor: NoAuthenticationInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor,
        httpErrorInterceptor: HttpErrorInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(noAuthenticationInterceptor)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(httpErrorInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoneyBoxApiService(retrofit: Retrofit): MoneyBoxApiService =
        retrofit.create(MoneyBoxApiService::class.java)
}