package com.moneybox.minimb.domain.repositorycontract.login

import com.moneybox.minimb.common.model.login.LoginRequestDto
import com.moneybox.minimb.common.model.login.LoginResponseDto
import com.moneybox.minimb.common.NetworkResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequestDto: LoginRequestDto): Flow<NetworkResult<LoginResponseDto>>
}