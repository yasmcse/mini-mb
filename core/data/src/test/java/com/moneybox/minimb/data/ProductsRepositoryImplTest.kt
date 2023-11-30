package com.moneybox.minimb.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.model.products.AllProductsDto
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.common.utils.TestDispatcherProvider
import com.moneybox.minimb.data.api.MoneyBoxApiService
import com.moneybox.minimb.data.repository.products.ProductsRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class ProductsRepositoryImplTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockMoneyBoxApiService = mockk<MoneyBoxApiService>()
    private lateinit var testDispatcher: DispatcherProvider
    private lateinit var sut: ProductsRepositoryImpl

    @Before
    fun setUp() {
        testDispatcher = TestDispatcherProvider()
        sut = ProductsRepositoryImpl(mockMoneyBoxApiService)
    }

    @Test
    fun `getInvestorProducts, returns successful NetworkResult`() =
        runBlocking(testDispatcher.default) {

            val allProductsDto = AllProductsDto(
                totalPlanValue = 100f,
                totalEarnings = 10f,
                totalEarningsAsPercentage = 10f,
                products = listOf()
            )

            val allProductsNetworkResult: NetworkResult.Success<AllProductsDto> =
                NetworkResult.Success(allProductsDto)

            // Create all products Flow
            val allProductsFlow = flow {
                emit(allProductsNetworkResult)
            }

            val allProductsResponse = Response.success(allProductsDto)

            coEvery { mockMoneyBoxApiService.getInvestorProducts() } returns allProductsResponse

            // collect the all products flow
            var expectedResult: NetworkResult<AllProductsDto>? = null
            allProductsFlow.collect {
                expectedResult = it
            }

            // Collect the flow for sut function call
            var sutResult: NetworkResult<AllProductsDto>? = null
            sut.getInvestorProducts().collect {
                sutResult = it
            }
            assertEquals(expectedResult, sutResult)
        }

    @Test
    fun `getInvestorProducts, returns error`() =
        runBlocking(testDispatcher.default) {
            // Test Data
            val allProductsNetworkResult: NetworkResult.Error =
                NetworkResult.Error(401, "Api call failed Response.error()")

            // Create all products Flow
            val allProductsFlow = flow {
                emit(allProductsNetworkResult)
            }

            val allProductsResponse =
                Response.error<AllProductsDto>(401, "Login Api call failed".toResponseBody())

            coEvery { mockMoneyBoxApiService.getInvestorProducts() } returns allProductsResponse

            var expectedResult: NetworkResult<AllProductsDto>? = null
            allProductsFlow.collect {
                expectedResult = it
            }

            // Collect the flow for sut function call
            var sutResult: NetworkResult<AllProductsDto>? = null
            sut.getInvestorProducts().collect {
                sutResult = it
            }

            assertEquals(expectedResult, sutResult)
        }
}




