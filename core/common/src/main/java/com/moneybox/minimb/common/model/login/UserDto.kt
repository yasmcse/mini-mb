package com.moneybox.minimb.common.model.login

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("UserId")
    val userId: String,
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("Email")
    val email: String
)