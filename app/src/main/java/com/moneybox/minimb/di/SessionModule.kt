package com.moneybox.minimb.di

import com.moneybox.minimb.MoneyBoxAuthSessionManagerImpl
import com.moneybox.minimb.common.AuthSessionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SessionModule {
    @Binds
    fun bindsAuthSessionManager(authSessionManager: MoneyBoxAuthSessionManagerImpl): AuthSessionManager
}