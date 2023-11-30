package com.moneybox.minimb.di

import com.moneybox.minimb.common.MoneyBoxNavigator
import com.moneybox.minimb.navigation.MoneyBoxNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindsNavigator(moneyBoxNavigator: MoneyBoxNavigatorImpl): MoneyBoxNavigator
}