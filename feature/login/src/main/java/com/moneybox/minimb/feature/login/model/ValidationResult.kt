package com.moneybox.minimb.feature.login.model


data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)