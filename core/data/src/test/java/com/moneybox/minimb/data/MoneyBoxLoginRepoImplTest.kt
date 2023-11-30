package com.moneybox.minimb.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.di.CommonModule
import com.moneybox.minimb.common.model.login.LoginRequestDto
import com.moneybox.minimb.common.model.login.LoginResponseDto
import com.moneybox.minimb.common.model.login.SessionDataDto
import com.moneybox.minimb.common.model.login.UserDto
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.common.utils.TestDispatcherProvider
import com.moneybox.minimb.data.api.MoneyBoxApiService
import com.moneybox.minimb.data.extensions.toLoginRequest
import com.moneybox.minimb.data.repository.login.MoneyBoxLoginRepoImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class MoneyBoxLoginRepoImplTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockMoneyBoxApiService = mockk<MoneyBoxApiService>()

    @CommonModule.Test
    private lateinit var testDispatcher: DispatcherProvider

    private lateinit var sut: MoneyBoxLoginRepoImpl


    @Before
    fun setUp() {
        testDispatcher = TestDispatcherProvider()
        sut = MoneyBoxLoginRepoImpl(
            mockMoneyBoxApiService,
            testDispatcher
        )
    }

    @Test
    fun `login, returns successful NetworkResult`() =
        runBlocking(testDispatcher.default) {

            // Create LoginRequestDto
            val loginRequestDto = LoginRequestDto(
                "jaeren+androidtest@moneyboxapp.com",
                "P455word12"
            )
            val loginResponseDto = LoginResponseDto(
                SessionDataDto("bearerToken"),
                UserDto("1", "firstName", "lastName", "email")
            )

            val loginNetworkResult: NetworkResult.Success<LoginResponseDto> =
                NetworkResult.Success(loginResponseDto)

            // Create Login Flow
            val loginFlow = flow {
                emit(loginNetworkResult)
            }

            val loginResponse = Response.success(loginResponseDto)

            coEvery { mockMoneyBoxApiService.login(loginRequestDto.toLoginRequest()) } returns loginResponse

            // collect the loginFlow
            var expectedResult: NetworkResult<LoginResponseDto>? = null
            loginFlow.collect {
                expectedResult = it
            }

            // Collect the flow for sut function call
            var sutResult: NetworkResult<LoginResponseDto>? = null
            sut.login(loginRequestDto).collect {
                sutResult = it
            }

            Assert.assertEquals(expectedResult, sutResult)
        }

    @Test
    fun `login, returns error NetworkResult`() =
        runBlocking(testDispatcher.default) {

            // Create LoginRequestDto
            val loginRequestDto = LoginRequestDto(
                "jaeren+androidtest@moneyboxapp.com",
                "P455word12"
            )

            val loginNetworkResult: NetworkResult.Error =
                NetworkResult.Error(401, "Api call failed Response.error()")

            // Create Login Flow
            val loginFlow = flow {
                emit(loginNetworkResult)
            }

            val loginResponse =
                Response.error<LoginResponseDto>(401, "Login Api call failed".toResponseBody())

            coEvery { mockMoneyBoxApiService.login(loginRequestDto.toLoginRequest()) } returns loginResponse

            // collect the loginFlow
            var expectedResult: NetworkResult<LoginResponseDto>? = null
            loginFlow.collect {
                expectedResult = it
            }

            // Collect the flow for sut function call
            var sutResult: NetworkResult<LoginResponseDto>? = null
            sut.login(loginRequestDto).collect {
                sutResult = it
            }

            Assert.assertEquals(expectedResult, sutResult)
        }
}