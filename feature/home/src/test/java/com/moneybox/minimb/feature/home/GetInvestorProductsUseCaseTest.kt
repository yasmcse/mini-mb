package com.moneybox.minimb.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.model.products.AllProducts
import com.moneybox.minimb.common.model.products.AllProductsDto
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.common.utils.TestDispatcherProvider
import com.moneybox.minimb.domain.repositorycontract.home.ProductsRepository
import com.moneybox.minimb.feature.home.mapper.ProductDomainMapper
import com.moneybox.minimb.feature.home.usecase.GetInvestorProductsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetInvestorProductsUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockProductsRepository = mockk<ProductsRepository>()
    private val mockProductsDomainMapper = mockk<ProductDomainMapper>()

    private lateinit var dispatcherProvider: TestDispatcherProvider
    private lateinit var sut: GetInvestorProductsUseCase

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        sut = GetInvestorProductsUseCase(mockProductsRepository, mockProductsDomainMapper)
    }

    @Test
    fun `when invoke gets called, returns AllProducts successfully`() =
        runBlocking(dispatcherProvider.default) {

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

            val expectedAllProducts = AllProducts(
                totalPlanValue = 100f,
                totalEarnings = 10f,
                totalEarningsAsPercentage = 10f,
                products = listOf()
            )

            coEvery { mockProductsRepository.getInvestorProducts() } returns allProductsDtoFlow
            coEvery { mockProductsDomainMapper.invoke(any()) } returns expectedAllProducts


            var allProducts: NetworkResult<AllProducts>? = null
            sut.invoke().collect {
                allProducts = it
            }

            assertEquals(
                expectedAllProducts,
                allProducts.let { (it as NetworkResult.Success).data })
        }

    @Test
    fun `when invoke gets called, returns error`() =
        runBlocking(dispatcherProvider.default) {

            val allProductsErrorState = NetworkResult.Error(400, "Api failed")

            val allProductsErrorFlow = flow {
                emit(allProductsErrorState)
            }

            coEvery { mockProductsRepository.getInvestorProducts() } returns allProductsErrorFlow

            var allProductsExpectedError: NetworkResult.Error? = null
            allProductsErrorFlow.collect {
                allProductsExpectedError = it
            }

            var allProducts: NetworkResult<AllProducts>? = null
            sut.invoke().collect {
                allProducts = it
            }

            assertEquals(
                allProductsExpectedError, allProducts
            )
        }

    @Test
    fun `when invoke gets called, returns Loading State`() =
        runBlocking(dispatcherProvider.default) {

            val allProductsLoadingState = NetworkResult.Loading

            val allProductsLoadingFlow = flow {
                emit(allProductsLoadingState)
            }

            coEvery { mockProductsRepository.getInvestorProducts() } returns allProductsLoadingFlow

            var allProductsExpectedLoading: NetworkResult.Loading? = null
            allProductsLoadingFlow.collect {
                allProductsExpectedLoading= it
            }

            var allProducts: NetworkResult<AllProducts>? = null
            sut.invoke().collect {
                allProducts = it
            }

            assertEquals(
                allProductsExpectedLoading, allProducts
            )
        }

    @Test
    fun `when invoke gets called, returns NoState State`() =
        runBlocking(dispatcherProvider.default) {

            val allProductsNoStateState = NetworkResult.NoState

            val allProductsNoStateFlow = flow {
                emit(allProductsNoStateState)
            }

            coEvery { mockProductsRepository.getInvestorProducts() } returns allProductsNoStateFlow

            var allProductsExpectedNoState: NetworkResult.NoState? = null
            allProductsNoStateFlow.collect {
                allProductsExpectedNoState= it
            }

            var allProducts: NetworkResult<AllProducts>? = null
            sut.invoke().collect {
                allProducts = it
            }

            assertEquals(
                allProductsExpectedNoState, allProducts
            )
        }
}
