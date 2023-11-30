package com.moneybox.minimb.common.di

import com.moneybox.minimb.common.MBTextProvider
import com.moneybox.minimb.common.TextProvider
import com.moneybox.minimb.common.utils.DefaultDispatcherProvider
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.common.utils.TestDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    fun bindsTextProvider(mbTextProvider: MBTextProvider): TextProvider
    @Feature
    @Binds
    fun bindsDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider

    @Test
    @Binds
    fun bindsTestDispatcherProvider(testDispatcherProvider: TestDispatcherProvider): DispatcherProvider

    @Qualifier
    annotation class Feature
    @Qualifier
    annotation class Test
}