package com.moneybox.minimb.data.repository.login

import com.moneybox.minimb.common.model.login.LoginRequestDto
import com.moneybox.minimb.data.api.MoneyBoxApiService
import com.moneybox.minimb.data.api.ApiResponse
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.di.CommonModule
import com.moneybox.minimb.data.extensions.toLoginRequest
import com.moneybox.minimb.common.model.login.LoginResponseDto
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.domain.repositorycontract.login.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoneyBoxLoginRepoImpl @Inject constructor(
    private val moneyBoxApiService: MoneyBoxApiService,
    @CommonModule.Feature private val dispatcher: DispatcherProvider
) : LoginRepository, ApiResponse() {
    override suspend fun login(loginRequestDto: LoginRequestDto): Flow<NetworkResult<LoginResponseDto>> =
        flow {
           emit(handleApiCall { moneyBoxApiService.login(loginRequestDto.toLoginRequest()) })
        }.flowOn(dispatcher.default)
    }