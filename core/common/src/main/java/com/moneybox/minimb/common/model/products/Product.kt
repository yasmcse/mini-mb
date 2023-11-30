package com.moneybox.minimb.common.model.products

data class Product(
    val id: Int,
    val product: ProductDetails,
    val moneybox: Float,
    val planValue: Float
)