package com.moneybox.minimb.data.api

import com.moneybox.minimb.data.models.login.LoginRequest
import com.moneybox.minimb.common.model.login.LoginResponseDto
import com.moneybox.minimb.common.model.products.AllProductsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MoneyBoxApiService {
    @POST("/users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponseDto>

    @AuthenticationRequired
    @GET("/investorproducts")
    suspend fun getInvestorProducts(): Response<AllProductsDto>

    @AuthenticationRequired
    @GET("/AllProductsResponse")
    suspend fun getAllProducts(): Response<AllProductsDto>
}