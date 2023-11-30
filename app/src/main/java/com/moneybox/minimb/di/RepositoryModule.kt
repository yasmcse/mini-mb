package com.moneybox.minimb.di

import com.moneybox.minimb.data.repository.login.MoneyBoxLoginRepoImpl
import com.moneybox.minimb.data.repository.products.ProductsRepositoryImpl
import com.moneybox.minimb.domain.repositorycontract.home.ProductsRepository
import com.moneybox.minimb.domain.repositorycontract.login.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsMoneyBoxLoginRepo(moneyBoxLoginRepo: MoneyBoxLoginRepoImpl): LoginRepository
    @Binds
    fun bindsProductsRepo(productsRepository: ProductsRepositoryImpl): ProductsRepository
}