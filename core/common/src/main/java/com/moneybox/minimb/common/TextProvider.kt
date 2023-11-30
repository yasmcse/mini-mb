package com.moneybox.minimb.common

import androidx.annotation.StringRes

interface TextProvider {
    fun getText(@StringRes stringResId: Int) : String
}