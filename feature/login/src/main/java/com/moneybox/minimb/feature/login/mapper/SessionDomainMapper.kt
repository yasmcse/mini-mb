package com.moneybox.minimb.feature.login.mapper

import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.model.login.User
import javax.inject.Inject

class SessionDomainMapper @Inject constructor() {
    operator fun invoke(login: Login): Pair<String,User> =
        Pair(login.session.bearerToken, login.user)
}

