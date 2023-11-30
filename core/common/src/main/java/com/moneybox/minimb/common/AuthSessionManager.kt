package com.moneybox.minimb.common

import com.moneybox.minimb.common.model.login.User

interface AuthSessionManager {
    val accessToken: String?
    val user: User?
    suspend fun updateUserInfo(user: User)
    suspend fun updateSession(accessToken: String, user: User)
    fun clear()
    fun isActive(): Boolean {
        return !accessToken.isNullOrBlank()
    }
}