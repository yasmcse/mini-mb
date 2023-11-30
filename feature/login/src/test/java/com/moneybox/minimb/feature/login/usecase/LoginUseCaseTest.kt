package com.moneybox.minimb.feature.login.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.AuthSessionManager
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.di.CommonModule
import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.model.login.LoginRequestDto
import com.moneybox.minimb.common.model.login.LoginResponseDto
import com.moneybox.minimb.common.model.login.SessionData
import com.moneybox.minimb.common.model.login.SessionDataDto
import com.moneybox.minimb.common.model.login.User
import com.moneybox.minimb.common.model.login.UserDto
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.common.utils.TestDispatcherProvider
import com.moneybox.minimb.domain.repositorycontract.login.LoginRepository
import com.moneybox.minimb.feature.login.mapper.LoginDomainMapper
import com.moneybox.minimb.feature.login.mapper.SessionDomainMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockLoginRepository = mockk<LoginRepository>()
    private val mockLoginDomainMapper = mockk<LoginDomainMapper>()
    private val sessionDomainMapper = mockk<SessionDomainMapper>()
    private val authSessionManager = mockk<AuthSessionManager>()
    @CommonModule.Test
    private lateinit var testDispatcher: DispatcherProvider
    private lateinit var sut: LoginUseCase

    @Before
    fun setUp() {
        testDispatcher = TestDispatcherProvider()
        sut = LoginUseCase(
            mockLoginRepository,
            mockLoginDomainMapper,
            sessionDomainMapper,
            authSessionManager,
            testDispatcher
        )
    }


    @Test
    fun `invoke, with email and password returns NetworkResult Loading`() =
        runBlocking(testDispatcher.default) {

            val email = "jaeren+androidtest@moneyboxapp.com"
            val password = "P455word12"
            // Create LoginRequestDto
            val loginRequestDto = LoginRequestDto(
                email = email,
                password = password
            )
            val loginResponse = NetworkResult.Loading

            // Create loginResponse Flow
            val loginResponseFlow = flow {
                emit(loginResponse)
            }

            val loginNetworkResult: NetworkResult.Loading = NetworkResult.Loading

            // Create Login Flow
            val loginFlow = flow {
                emit(loginNetworkResult)
            }

            coEvery { mockLoginRepository.login(loginRequestDto) } returns loginResponseFlow

            // collect the loginFlow
            var expectedResult: NetworkResult<Login>? = null
            loginFlow.collect {
                expectedResult = it
            }

            // Collect the flow for sut function call
            var sutResult: NetworkResult<Login>? = null
            sut.invoke(loginRequestDto.email, loginRequestDto.password).collect {
                sutResult = it
            }
            assertEquals(expectedResult, sutResult)
        }

    @Test
    fun `invoke, with email and password returns NetworkResult Success Login`() =

        runBlocking(testDispatcher.default) {

            val email = "jaeren+androidtest@moneyboxapp.com"
            val password = "P455word12"
            // Create LoginRequestDto
            val loginRequestDto = LoginRequestDto(
                email = email,
                password = password
            )


            val loginResponseDto = LoginResponseDto(
                SessionDataDto("bearerToken"),
                UserDto("1", "firstName", "lastName", "email")
            )
            val loginResponse = NetworkResult.Success(loginResponseDto)

            // Create loginResponse Flow
            val loginResponseFlow = flow {
                emit(loginResponse)
            }

            // Create Login Test Data
            val login = Login(
                SessionData("bearerToken"),
                User("1", "firstName", "lastName", "email")
            )

            val loginNetworkResult: NetworkResult.Success<Login> = NetworkResult.Success(login)

            // Create Login Flow
            val loginFlow = flow {
                emit(loginNetworkResult)
            }
            val userInfoPair = Pair(login.session.bearerToken, login.user)

            coEvery { mockLoginRepository.login(loginRequestDto) } returns loginResponseFlow
            coEvery { mockLoginDomainMapper(loginResponseDto) } returns login
            coEvery { sessionDomainMapper(login) } returns userInfoPair
            coEvery {
                authSessionManager.updateSession(
                    userInfoPair.first,
                    userInfoPair.second
                )
            } returns Unit

            // collect the loginFlow
            var expectedResult: NetworkResult<Login>? = null
            loginFlow.collect {
                expectedResult = it
            }

            // Collect the flow for sut function call
            var sutResult: NetworkResult<Login>? = null
            sut.invoke(email, password).collect {
                sutResult = it
            }

            assertEquals(expectedResult, sutResult)
        }

    @Test
    fun `invoke, with email and password returns NetworkResult Error`() =

        runBlocking(testDispatcher.default) {

            val email = "jaeren+androidtest@moneyboxapp.com"
            val password = "P455word12"
            // Create LoginRequestDto
            val loginRequestDto = LoginRequestDto(
                email = email,
                password = password
            )
            val loginResponse = NetworkResult.Error(401, "Login Api failed")

            // Create loginResponse Flow
            val loginResponseFlow = flow {
                emit(loginResponse)
            }

            val loginNetworkResult: NetworkResult.Error =
                NetworkResult.Error(401, "Login Api failed")

            // Create Login Flow
            val loginFlow = flow {
                emit(loginNetworkResult)
            }

            coEvery { mockLoginRepository.login(loginRequestDto) } returns loginResponseFlow

            // collect the loginFlow
            var expectedResult: NetworkResult<Login>? = null
            loginFlow.collect {
                expectedResult = it
            }

            // Collect the flow for sut function call
            var sutResult: NetworkResult<Login>? = null
            sut.invoke(loginRequestDto.email, loginRequestDto.password).collect {
                sutResult = it
            }
            assertEquals(expectedResult, sutResult)
        }

    @Test
    fun `invoke, with email and password returns NetworkResult NoState`() =

        runBlocking(testDispatcher.default) {

            val email = "jaeren+androidtest@moneyboxapp.com"
            val password = "P455word12"
            // Create LoginRequestDto
            val loginRequestDto = LoginRequestDto(
                email = email,
                password = password
            )
            val loginResponse = NetworkResult.NoState

            // Create loginResponse Flow
            val loginResponseFlow = flow {
                emit(loginResponse)
            }

            val loginNetworkResult: NetworkResult.NoState = NetworkResult.NoState

            // Create Login Flow
            val loginFlow = flow {
                emit(loginNetworkResult)
            }

            coEvery { mockLoginRepository.login(loginRequestDto) } returns loginResponseFlow

            // collect the loginFlow
            var expectedResult: NetworkResult<Login>? = null
            loginFlow.collect {
                expectedResult = it
            }

            // Collect the flow for sut function call
            var sutResult: NetworkResult<Login>? = null
            sut.invoke(loginRequestDto.email, loginRequestDto.password).collect {
                sutResult = it
            }
            assertEquals(expectedResult, sutResult)
        }
}