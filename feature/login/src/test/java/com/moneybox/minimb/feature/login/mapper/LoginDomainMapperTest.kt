package com.moneybox.minimb.feature.login.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.model.login.LoginResponseDto
import com.moneybox.minimb.common.model.login.SessionData
import com.moneybox.minimb.common.model.login.SessionDataDto
import com.moneybox.minimb.common.model.login.User
import com.moneybox.minimb.common.model.login.UserDto
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.feature.login.mapper.LoginDomainMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class LoginDomainMapperTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: LoginDomainMapper

    @Before
    fun setUp() {
        sut = LoginDomainMapper()
    }

    @Test
    fun `invoke, with loginResponseDto returns the domain login object`() = runTest {
        // Login test data
        val loginResponseDto = LoginResponseDto(
            SessionDataDto("bearerToken"),
            UserDto("1", "firstName", "lastName", "email")
        )
        val expected = Login(
            SessionData("bearerToken"),
            User("1", "firstName", "lastName", "email")
        )
        assertEquals(expected, sut.invoke(loginResponseDto))
    }
}