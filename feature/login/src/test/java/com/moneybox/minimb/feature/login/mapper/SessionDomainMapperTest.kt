package com.moneybox.minimb.feature.login.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.model.login.SessionData
import com.moneybox.minimb.common.model.login.User
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.feature.login.mapper.SessionDomainMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SessionDomainMapperTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: SessionDomainMapper

    @Before
    fun setUp() {
        sut = SessionDomainMapper()
    }

    @Test
    fun `invoke, with login details returns the pair of bearer token and User`() = runTest {
        // Login test data
        val login = Login(
            SessionData("bearerToken"),
            User("1", "firstName", "lastName", "email")
        )

        val expected = Pair("bearerToken", User("1", "firstName", "lastName", "email"))
        assertEquals(expected, sut.invoke(login))
    }
}