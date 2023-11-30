package com.moneybox.minimb.common

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MBTextProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : TextProvider {

    override fun getText(@StringRes stringResId: Int): String = context.getString(stringResId)
}