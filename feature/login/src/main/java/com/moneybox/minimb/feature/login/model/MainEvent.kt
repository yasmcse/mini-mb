package com.moneybox.minimb.feature.login.model

sealed class MainEvent {
    data class EmailChanged(val email: String) : MainEvent()
    data class PasswordChanged(val password: String) : MainEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : MainEvent()
    object Submit : MainEvent()
}