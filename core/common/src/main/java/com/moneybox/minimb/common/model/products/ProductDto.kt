package com.moneybox.minimb.common.model.products

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Product")
    val product: ProductDetailsDto,
    @SerializedName("Moneybox")
    val moneybox: Float,
    @SerializedName("PlanValue")
    val planValue: Float
)