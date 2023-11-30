package com.moneybox.minimb.data.extensions

import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.model.login.SessionData
import com.moneybox.minimb.common.model.login.User
import com.moneybox.minimb.data.models.login.LoginRequest
import com.moneybox.minimb.common.model.login.LoginResponseDto


fun com.moneybox.minimb.common.model.login.LoginRequestDto.toLoginRequest(): LoginRequest {
    return LoginRequest("", "", "", this.email, this.password)
}

fun LoginResponseDto.toLoginDomain(): Login =
    this.let {
        Login(
            SessionData(it.session.bearerToken),
            User(it.user.userId, it.user.firstName, it.user.lastName, it.user.email)
        )
    }