package com.moneybox.minimb.common.model.products

import com.google.gson.annotations.SerializedName

data class AllProductsDto(
    @SerializedName("TotalPlanValue")
    val totalPlanValue: Float,
    @SerializedName("TotalEarnings")
    val totalEarnings: Float,
    @SerializedName("TotalEarningsAsPercentage")
    val totalEarningsAsPercentage: Float? = null,
    @SerializedName("ProductResponses")
    val products: List<ProductDto>,
)