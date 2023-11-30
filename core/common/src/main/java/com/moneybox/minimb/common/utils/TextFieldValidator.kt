package com.moneybox.minimb.common.utils

import androidx.core.util.PatternsCompat

fun isNumber(value: String): Boolean {
    return value.isEmpty() || Regex("^\\d+\$").matches(value)
}

fun isEmailValid(email: String): Boolean {
    return PatternsCompat.EMAIL_ADDRESS.pattern().toRegex().matches(email)
}

fun isPasswordValid(password: String): Boolean {
    return password.any { it.isDigit() } &&
            password.any { it.isLetter() }
}