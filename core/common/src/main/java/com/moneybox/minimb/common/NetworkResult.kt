package com.moneybox.minimb.common

sealed interface NetworkResult<out T> {
    data class Success<out T>(val data: T): NetworkResult<T>
    data class Error(val code:Int?,val message: String): NetworkResult<Nothing>
    object Loading: NetworkResult<Nothing>
    object NoState: NetworkResult<Nothing>
}