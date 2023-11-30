package com.moneybox.minimb.feature.home.mapper

import com.moneybox.minimb.common.model.products.AllProducts
import com.moneybox.minimb.common.model.products.AllProductsDto
import com.moneybox.minimb.common.model.products.Product
import com.moneybox.minimb.common.model.products.ProductDetails
import javax.inject.Inject

class ProductDomainMapper @Inject constructor() {
    operator fun invoke(productsDto: AllProductsDto): AllProducts =
        AllProducts(
            totalPlanValue = productsDto.totalPlanValue,
            totalEarnings = productsDto.totalEarnings,
            totalEarningsAsPercentage = productsDto.totalEarningsAsPercentage,
            products = productsDto.products.map {
                Product(
                    id = it.id,
                    product = ProductDetails(it.product.friendlyName),
                    moneybox = it.moneybox,
                    planValue = it.planValue,
                )
            }
        )
}