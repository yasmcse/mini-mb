package com.moneybox.minimb.domain.repositorycontract.home

import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.model.products.AllProductsDto
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getInvestorProducts(): Flow<NetworkResult<AllProductsDto>>
}