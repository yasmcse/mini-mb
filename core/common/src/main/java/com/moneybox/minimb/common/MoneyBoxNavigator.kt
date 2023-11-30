package com.moneybox.minimb.common

import androidx.fragment.app.Fragment


interface MoneyBoxNavigator {
    fun navigateToMainActivity()
    fun Fragment.navigateToHomeScreen()
    fun Fragment.goBack()
}