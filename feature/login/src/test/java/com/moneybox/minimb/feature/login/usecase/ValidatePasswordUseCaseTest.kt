package com.moneybox.minimb.feature.login.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.MBTextProvider
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.feature.login.model.ValidationResult
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatePasswordUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mockMBTextProvider = mockk<MBTextProvider>()
    private lateinit var sut: ValidatePasswordUseCase


    @Before
    fun setUp() {
        sut = ValidatePasswordUseCase(mockMBTextProvider)
    }

    @Test
    fun `when password is blank then return error`() {
        val expected = ValidationResult(
            successful = false,
            errorMessage = "Password can't be blank"
        )
        every { mockMBTextProvider.getText(any()) } returns expected.errorMessage.toString()

        sut = ValidatePasswordUseCase(mockMBTextProvider)
        assertEquals(expected, sut(""))
    }

    @Test
    fun `when password is invalid then return error`() {
        val expected = ValidationResult(
            successful = false,
            errorMessage = "Password should be atleast 8 characters"
        )
        every { mockMBTextProvider.getText(any()) } returns expected.errorMessage.toString()

        sut = ValidatePasswordUseCase(mockMBTextProvider)
        assertEquals(expected, sut("kjssd"))
    }
}