package com.moneybox.minimb.feature.login.mapper

import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.model.login.LoginResponseDto
import com.moneybox.minimb.common.model.login.SessionDataDto
import com.moneybox.minimb.common.model.login.SessionData
import com.moneybox.minimb.common.model.login.User
import com.moneybox.minimb.common.model.login.UserDto
import javax.inject.Inject

class LoginDomainMapper @Inject constructor() {
    operator fun invoke(loginResponseDto: LoginResponseDto) = Login(
        session = loginResponseDto.session.toSessionDomain(),
        user = loginResponseDto.user.toUserDomain()
    )

    private fun SessionDataDto.toSessionDomain() = SessionData(
        bearerToken = bearerToken
    )

    private fun UserDto.toUserDomain() = User(
        userId = this.userId,
        firstName = firstName,
        lastName = lastName,
        email = email
    )
}