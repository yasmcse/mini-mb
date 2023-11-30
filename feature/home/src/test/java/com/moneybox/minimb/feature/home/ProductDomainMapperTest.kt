package com.moneybox.minimb.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.model.products.AllProductsDto
import com.moneybox.minimb.common.model.products.ProductDetailsDto
import com.moneybox.minimb.common.model.products.ProductDto
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.feature.home.mapper.ProductDomainMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProductDomainMapperTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: ProductDomainMapper

    @Before
    fun setUp() {
        sut = ProductDomainMapper()
    }

    @Test
    fun `when invokes,maps the AllProductsDto to AllProducts`() {
        // Test Data
        val allProductsDto = AllProductsDto(
            totalPlanValue = 100f,
            totalEarnings = 10f,
            totalEarningsAsPercentage = 10f,
            products = listOf(
                ProductDto(
                    id = 1,
                    product = ProductDetailsDto("ISA"),
                    moneybox = 10f,
                    planValue = 100f
                )
            )
        )

        val result = sut.invoke(allProductsDto)

        assert(result.totalPlanValue == 100f)
        assert(result.totalEarnings == 10f)
        assert(result.totalEarningsAsPercentage == 10f)
        assert(result.products.size == 1)
        assert(result.products[0].id == 1)
        assert(result.products[0].product.friendlyName == "ISA")
        assert(result.products[0].moneybox == 10f)
        assert(result.products[0].planValue == 100f)
    }
}