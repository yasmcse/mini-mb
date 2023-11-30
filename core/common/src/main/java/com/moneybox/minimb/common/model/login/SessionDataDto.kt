package com.moneybox.minimb.common.model.login

import com.google.gson.annotations.SerializedName

data class SessionDataDto(
    @SerializedName("BearerToken")
    val bearerToken: String
)