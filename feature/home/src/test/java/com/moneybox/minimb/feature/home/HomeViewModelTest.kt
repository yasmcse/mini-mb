package com.moneybox.minimb.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.di.CommonModule
import com.moneybox.minimb.common.model.products.AllProducts
import com.moneybox.minimb.common.model.products.AllProductsDto
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.common.utils.TestDispatcherProvider
import com.moneybox.minimb.domain.repositorycontract.home.ProductsRepository
import com.moneybox.minimb.feature.home.mapper.ProductDomainMapper
import com.moneybox.minimb.feature.home.ui.HomeViewModel
import com.moneybox.minimb.feature.home.usecase.GetInvestorProductsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockProductsRepository = mockk<ProductsRepository>()
    private val mockProductDomainMapper = mockk<ProductDomainMapper>()
    private val mockGetInvestorProductsUseCase = mockk<GetInvestorProductsUseCase>()

    @CommonModule.Test
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var sut: HomeViewModel

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        sut = HomeViewModel(mockGetInvestorProductsUseCase, dispatcherProvider)
    }

    @Test
    fun `when getInvestorProducts gets called, returns AllProducts successfully`() {
        runBlocking(dispatcherProvider.default) {
            // test data
            val allProducts = AllProducts(
                totalPlanValue = 100f,
                totalEarnings = 10f,
                totalEarningsAsPercentage = 10f,
                products = listOf()
            )
            val allProductsNetworkResult = NetworkResult.Success(allProducts)
            val allProductsFlow = flow {
                emit(allProductsNetworkResult)
            }


            val allProductsDto = AllProductsDto(
                totalPlanValue = 100f,
                totalEarnings = 10f,
                totalEarningsAsPercentage = 10f,
                products = listOf()
            )
            val allProductsDtoNetworkResult = NetworkResult.Success(allProductsDto)

            val allProductsDtoFlow = flow {
                emit(allProductsDtoNetworkResult)
            }

            val allProductsNetworkSuccess: NetworkResult.Success<AllProducts> =
                NetworkResult.Success(
                    AllProducts(
                        totalPlanValue = 100f,
                        totalEarnings = 10f,
                        totalEarningsAsPercentage = 10f,
                        products = listOf()
                    )
                )
            coEvery { mockProductsRepository.getInvestorProducts() } returns allProductsDtoFlow
            coEvery { mockProductDomainMapper.invoke(allProductsDto) } returns allProducts
            coEvery { mockGetInvestorProductsUseCase() } returns allProductsFlow

            sut.getInvestorProducts()
            assertEquals(
                allProductsNetworkSuccess,
                sut.products.value
            )
        }
    }

    @Test
    fun `when getInvestorProducts gets called, returns error state`() {
        runBlocking(dispatcherProvider.default) {
            // test data
            val allProductsNetworkResult = NetworkResult.Error(400, "Api failed")
            val allProductsFlow = flow {
                emit(allProductsNetworkResult)
            }
            coEvery { mockGetInvestorProductsUseCase() } returns allProductsFlow

            sut.getInvestorProducts()
            assertEquals(
                allProductsNetworkResult,
                sut.products.value
            )
        }
    }

    @Test
    fun `when getInvestorProducts gets called, returns Loading state`() {
        runBlocking(dispatcherProvider.default) {
            // test data
            val allProductsNetworkResult = NetworkResult.Loading
            val allProductsFlow = flow {
                emit(allProductsNetworkResult)
            }
            coEvery { mockGetInvestorProductsUseCase() } returns allProductsFlow

            sut.getInvestorProducts()
            assertEquals(
                allProductsNetworkResult,
                sut.products.value
            )
        }
    }

    @Test
    fun `when getInvestorProducts gets called, returns NoState`() {
        runBlocking(dispatcherProvider.default) {
            // test data
            val allProductsNetworkResult = NetworkResult.NoState
            val allProductsFlow = flow {
                emit(allProductsNetworkResult)
            }
            coEvery { mockGetInvestorProductsUseCase() } returns allProductsFlow

            sut.getInvestorProducts()
            assertEquals(
                allProductsNetworkResult,
                sut.products.value
            )
        }
    }
}