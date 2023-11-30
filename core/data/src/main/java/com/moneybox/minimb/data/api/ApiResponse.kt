package com.moneybox.minimb.data.api

import com.moneybox.minimb.common.NetworkResult
import retrofit2.Response

abstract class ApiResponse {
    suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        val response = apiCall()
        try {
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error(response.code(), response.message())
        } catch (e: Exception) {
            return error(response.code(), response.message())
        }
    }

    private fun <T> error(errorCode:Int?,errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(errorCode!!,"Api call failed $errorMessage")
}