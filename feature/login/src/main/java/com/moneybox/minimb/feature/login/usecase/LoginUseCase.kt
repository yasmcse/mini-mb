package com.moneybox.minimb.feature.login.usecase

import com.moneybox.minimb.common.AuthSessionManager
import com.moneybox.minimb.common.model.login.LoginRequestDto
import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.di.CommonModule
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.domain.repositorycontract.login.LoginRepository
import com.moneybox.minimb.feature.login.mapper.LoginDomainMapper
import com.moneybox.minimb.feature.login.mapper.SessionDomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val loginDomainMapper: LoginDomainMapper,
    private val sessionDomainMapper: SessionDomainMapper,
    private val sessionManager: AuthSessionManager,
    @CommonModule.Feature private val dispatcher: DispatcherProvider
) {
    operator fun invoke(email: String, password: String): Flow<NetworkResult<Login>> =
        flow {
            emit(NetworkResult.Loading)
            loginRepository.login(
                LoginRequestDto(
                    email = email,
                    password = password
                )
            ).map {
                when (it) {
                    is NetworkResult.Loading -> {
                        return@map NetworkResult.Loading
                    }

                    is NetworkResult.Success -> {
                        val loginDomain = loginDomainMapper(it.data)
                        val userInfoPair = sessionDomainMapper(loginDomain)
                        sessionManager.updateSession(userInfoPair.first, userInfoPair.second)
                        return@map NetworkResult.Success(loginDomain)
                    }

                    is NetworkResult.Error -> {
                        return@map NetworkResult.Error(it.code, it.message)
                    }

                    is NetworkResult.NoState -> {
                        return@map NetworkResult.NoState
                    }
                }
            }.collect {
                emit(it)
            }
        }
}