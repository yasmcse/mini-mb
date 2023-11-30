package com.moneybox.minimb.common.model.products

import com.google.gson.annotations.SerializedName

data class ProductDetailsDto(
    @SerializedName("FriendlyName")
    val friendlyName: String
)