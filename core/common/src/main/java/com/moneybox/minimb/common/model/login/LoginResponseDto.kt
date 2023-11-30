package com.moneybox.minimb.common.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("Session")
    val session: SessionDataDto,
    @SerializedName("User")
    val user: UserDto
)
