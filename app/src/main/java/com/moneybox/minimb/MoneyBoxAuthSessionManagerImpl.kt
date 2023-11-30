package com.moneybox.minimb

import android.content.Context
import com.moneybox.minimb.common.AuthSessionManager
import com.moneybox.minimb.common.model.login.User
import com.moneybox.minimb.common.utils.getDataClassAsString
import com.moneybox.minimb.common.utils.getStringAsDataClass
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MoneyBoxAuthSessionManagerImpl @Inject constructor(
    @ApplicationContext context: Context
) : AuthSessionManager {

    private val pref = context.getSharedPreferences(SESSION_MANAGER_FILE, Context.MODE_PRIVATE)

    override val accessToken: String?
        get() = pref.getString(ACCESS_TOKEN, null)

    override val user: User?
        get() = pref.getString(USER_INFO, null)
            ?.let { getStringAsDataClass(it, User::class.java) }

    override suspend fun updateUserInfo(user: User) {
        pref.edit().putString(USER_INFO, getDataClassAsString(user)).apply()
    }

    override suspend fun updateSession(accessToken: String, user: User) {
        pref.edit().putString(ACCESS_TOKEN, accessToken).apply()
        pref.edit().putString(USER_INFO, getDataClassAsString(user)).apply()
    }

    override  fun clear() {
        pref.edit().clear().apply()
    }

    companion object {
        private const val SESSION_MANAGER_FILE = "session_manager"
        private const val ACCESS_TOKEN = "key_access_token"
        private const val USER_INFO = "user_info"
    }
}