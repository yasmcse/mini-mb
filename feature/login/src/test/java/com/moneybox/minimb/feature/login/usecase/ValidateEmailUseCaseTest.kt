package com.moneybox.minimb.feature.login.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.MBTextProvider
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.common.utils.isEmailValid
import com.moneybox.minimb.feature.login.model.ValidationResult
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ValidateEmailUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mockMBTextProvider = mockk<MBTextProvider>()
    private lateinit var sut: ValidateEmailUseCase


    @Before
    fun setUp() {
        sut = ValidateEmailUseCase(mockMBTextProvider)
    }

    @Test
    fun `when email is blank then return error`() {
        val expected = ValidationResult(
            successful = false,
            errorMessage = "Email can't be blank"
        )
        every { mockMBTextProvider.getText(any()) } returns expected.errorMessage.toString()

        assertEquals(expected, sut.invoke(""))
    }

    @Test
    fun `when email is invalid then return error`() {
        val expected = ValidationResult(
            successful = false,
            errorMessage = "The email can not be blank"
        )
        every { isEmailValid("kjskdjksjd") } returns false
        every { mockMBTextProvider.getText(any()) } returns expected.errorMessage.toString()

        sut = ValidateEmailUseCase(mockMBTextProvider)
        assertEquals(expected, sut.invoke("kjskdjksjd"))
    }
}