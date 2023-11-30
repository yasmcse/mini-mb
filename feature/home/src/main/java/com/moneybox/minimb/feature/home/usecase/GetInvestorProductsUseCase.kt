package com.moneybox.minimb.feature.home.usecase

import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.model.products.AllProducts
import com.moneybox.minimb.domain.repositorycontract.home.ProductsRepository
import com.moneybox.minimb.feature.home.mapper.ProductDomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetInvestorProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val productDomainMapper: ProductDomainMapper
) {
     operator fun invoke(): Flow<NetworkResult<AllProducts>> =
        flow {
            productsRepository.getInvestorProducts()
                .map {
                    when (it) {
                        is NetworkResult.Loading -> {
                           return@map NetworkResult.Loading
                        }

                        is NetworkResult.Success -> {
                           return@map NetworkResult.Success(productDomainMapper(it.data))
                        }

                        is NetworkResult.Error -> {
                           return@map NetworkResult.Error(it.code,it.message)
                        }
                        is NetworkResult.NoState -> {
                           return@map NetworkResult.NoState
                        }
                    }
                }.collect {
                    emit(it)
                }
        }
    }