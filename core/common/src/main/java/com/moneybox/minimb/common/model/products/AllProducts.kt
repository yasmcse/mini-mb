package com.moneybox.minimb.common.model.products

data class AllProducts(
    val totalPlanValue: Float,
    val totalEarnings: Float,
    val totalEarningsAsPercentage: Float? = null,
    val products: List<Product>
)
