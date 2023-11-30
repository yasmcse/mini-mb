package com.moneybox.minimb.data.repository.products

import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.model.products.AllProductsDto
import com.moneybox.minimb.data.api.MoneyBoxApiService
import com.moneybox.minimb.data.api.ApiResponse
import com.moneybox.minimb.domain.repositorycontract.home.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val moneyBoxApiService: MoneyBoxApiService
) : ProductsRepository, ApiResponse() {
    override suspend fun getInvestorProducts(): Flow<NetworkResult<AllProductsDto>> =
        flow {
            emit(handleApiCall { moneyBoxApiService.getInvestorProducts() })
        }.flowOn(Dispatchers.Default)
}