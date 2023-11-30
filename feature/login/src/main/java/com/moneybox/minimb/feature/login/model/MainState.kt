package com.moneybox.minimb.feature.login.model


data class MainState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isVisiblePassword: Boolean = false
)