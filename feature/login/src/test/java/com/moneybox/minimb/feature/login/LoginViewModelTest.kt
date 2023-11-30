package com.moneybox.minimb.feature.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moneybox.minimb.common.TextProvider
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.common.utils.MainCoroutineRule
import com.moneybox.minimb.common.utils.TestDispatcherProvider
import com.moneybox.minimb.feature.login.model.MainEvent
import com.moneybox.minimb.feature.login.model.ValidationResult
import com.moneybox.minimb.feature.login.ui.LoginViewModel
import com.moneybox.minimb.feature.login.usecase.LoginUseCase
import com.moneybox.minimb.feature.login.usecase.ValidateEmailUseCase
import com.moneybox.minimb.feature.login.usecase.ValidatePasswordUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineDispatcher = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mockLoginUseCase = mockk<LoginUseCase>()
    private val mockTextProvider = mockk<TextProvider>()
    private val mockValidateEmailUseCase = mockk<ValidateEmailUseCase>()
    private val mockValidatePasswordUseCase = mockk<ValidatePasswordUseCase>()
    private lateinit var testDispatcher: DispatcherProvider
    private lateinit var sut: LoginViewModel


    @Before
    fun setUp() {
        testDispatcher = TestDispatcherProvider()
        sut = LoginViewModel(
            mockLoginUseCase,
            mockValidateEmailUseCase,
            mockValidatePasswordUseCase,
            testDispatcher
        )
    }

    @Test
    fun `onEvent, when input invalid email then validateEmail gets returns error`() {
        runBlocking(testDispatcher.default) {
            val emailEvent = MainEvent.EmailChanged("yasgmailcom")

            val emailResult = ValidationResult(
                successful = false,
                errorMessage = "error email"
            )
            coEvery { mockTextProvider.getText(any()) } returns emailResult.errorMessage.toString()
            coEvery { mockValidateEmailUseCase.invoke(emailEvent.email) } returns emailResult

            sut.onEvent(emailEvent)
            assertEquals(emailResult.errorMessage, sut.formState.emailError)
        }
    }

    @Test
    fun `onEvent, when input valid email then validateEmail gets returns success`() {
        runBlocking(testDispatcher.default) {
            val emailEvent = MainEvent.EmailChanged("yas@hey.com")

            val emailResult = ValidationResult(
                successful = true,
                errorMessage = null
            )
            coEvery { mockTextProvider.getText(any()) } returns emailResult.errorMessage.toString()
            coEvery { mockValidateEmailUseCase.invoke(emailEvent.email) } returns emailResult

            sut.onEvent(emailEvent)
            assertEquals(emailResult.errorMessage, sut.formState.emailError)
        }
    }

    @Test
    fun `onEvent, when password less than 8 characters, then validatePassword returns error `() {
        runBlocking(testDispatcher.default) {
            val passwordEvent = MainEvent.PasswordChanged("Pkkok")

            val passwordResult = ValidationResult(
                successful = false,
                errorMessage = "Password should be atleast 8 characters"
            )
            coEvery { mockTextProvider.getText(any()) } returns passwordResult.errorMessage.toString()
            coEvery { mockValidatePasswordUseCase.invoke(passwordEvent.password) } returns passwordResult

            sut.onEvent(passwordEvent)
            assertEquals(passwordResult.errorMessage, sut.formState.passwordError)
        }
    }

    @Test
    fun `onEvent, when input valid password, then validatePassword returns success `() {
        runBlocking(testDispatcher.default) {
            val passwordEvent = MainEvent.PasswordChanged("Pkkok12f")

            val passwordResult = ValidationResult(
                successful = true,
                errorMessage = null
            )

            coEvery { mockTextProvider.getText(any()) } returns passwordResult.errorMessage.toString()
            coEvery { mockValidatePasswordUseCase.invoke(passwordEvent.password) } returns passwordResult

            sut.onEvent(passwordEvent)
            assertEquals(passwordResult.errorMessage, sut.formState.passwordError)
        }
    }

    @Test
    fun `onEvent with submit state, when valid email and password is input then submit is successfull`() {
        runBlocking(testDispatcher.default) {
            val emailEvent = MainEvent.EmailChanged("ijijijji@mail.com")
            val passwordEvent = MainEvent.PasswordChanged("Pkkok123")

            val emailResult = ValidationResult(
                successful = true,
                errorMessage = null
            )
            val passwordResult = ValidationResult(
                successful = true,
                errorMessage = null
            )

            coEvery { mockValidateEmailUseCase.invoke(any()) } returns emailResult
            coEvery { mockValidatePasswordUseCase.invoke(any()) } returns passwordResult

            sut.onEvent(emailEvent)
            sut.onEvent(passwordEvent)
            assertEquals(emailResult.errorMessage, sut.formState.emailError)
            assertEquals(passwordResult.errorMessage, sut.formState.passwordError)
        }
    }

    @Test
    fun `onEvent with submit state, when valid email and invalid password is input then submit fails`() {
        runBlocking(testDispatcher.default) {
            val emailEvent = MainEvent.EmailChanged("ijijijji@mail.com")
            val passwordEvent = MainEvent.PasswordChanged("Pkk")

            val emailResult = ValidationResult(
                successful = true,
                errorMessage = null
            )
            val passwordResult = ValidationResult(
                successful = false,
                errorMessage = "Password should be atleast 8 characters"
            )

            coEvery { mockValidateEmailUseCase.invoke(any()) } returns emailResult
            coEvery { mockValidatePasswordUseCase.invoke(any()) } returns passwordResult

            sut.onEvent(emailEvent)
            assertEquals(emailResult.errorMessage, sut.formState.emailError)

            sut.onEvent(passwordEvent)

            assertEquals(passwordResult.errorMessage, sut.formState.passwordError)

        }
    }

    @Test
    fun `onEvent with submit state, when invalid email valid password is input then submit fails`() {
        runBlocking(testDispatcher.default) {
            val emailEvent = MainEvent.EmailChanged("ijijijjimail.com")
            val passwordEvent = MainEvent.PasswordChanged("Pkklklkl12")

            val emailResult = ValidationResult(
                successful = false,
                errorMessage = "That\\'s not a valid email"
            )
            val passwordResult = ValidationResult(
                successful = true,
                errorMessage = null
            )

            coEvery { mockValidateEmailUseCase.invoke(any()) } returns emailResult
            coEvery { mockValidatePasswordUseCase.invoke(any()) } returns passwordResult

            sut.onEvent(emailEvent)

            assertEquals(emailResult.errorMessage, sut.formState.emailError)

            sut.onEvent(passwordEvent)

            assertEquals(passwordResult.errorMessage, sut.formState.passwordError)

        }
    }

    @Test
    fun `onEvent with visible password successfull`() {
        runBlocking(testDispatcher.default) {
            val visiblePassword = MainEvent.VisiblePassword(true)
            sut.onEvent(visiblePassword)

            assertTrue(sut.formState.isVisiblePassword)
        }
    }

    @Test
    fun `onEvent with visible password error`() {
        runBlocking(testDispatcher.default) {
            val visiblePassword = MainEvent.VisiblePassword(false)
            sut.onEvent(visiblePassword)

            assertFalse(sut.formState.isVisiblePassword)
        }
    }


    @Test
    fun `onEvent with submit state, when valid email and password is input returns correct login `() {
        runBlocking(testDispatcher.default) {
            val submitEvent = MainEvent.Submit

            val emailResult = ValidationResult(
                successful = true,
                errorMessage = null
            )
            val passwordResult = ValidationResult(
                successful = true,
                errorMessage = null
            )

            coEvery { mockValidateEmailUseCase.invoke(any()) } returns emailResult
            coEvery { mockValidatePasswordUseCase.invoke(any()) } returns passwordResult

            sut.onEvent(submitEvent)

            assertEquals(emailResult.errorMessage, sut.formState.emailError)


            assertEquals(passwordResult.errorMessage, sut.formState.passwordError)
        }
    }

    @Test
    fun `onEvent, when input invalid email then validateEmail returns false`() {
        runBlocking(testDispatcher.default) {
            val emailEvent = MainEvent.EmailChanged("yasgmailcom")

            val emailResult = ValidationResult(
                successful = false,
                errorMessage = "error email"
            )
            coEvery { mockTextProvider.getText(any()) } returns emailResult.errorMessage.toString()
            coEvery { mockValidateEmailUseCase.invoke(emailEvent.email) } returns emailResult

            sut.onEvent(emailEvent)

            assertFalse(sut.validateEmail())
        }
    }

    @Test
    fun `onEvent, when input valid email then validateEmail returns true`() {
        runBlocking(testDispatcher.default) {
            val emailEvent = MainEvent.EmailChanged("yasgmailcom")

            val emailResult = ValidationResult(
                successful = true,
                errorMessage = null
            )
            coEvery { mockTextProvider.getText(any()) } returns emailResult.errorMessage.toString()
            coEvery { mockValidateEmailUseCase.invoke(emailEvent.email) } returns emailResult

            sut.onEvent(emailEvent)

            assertTrue(sut.validateEmail())
        }
    }

    @Test
    fun `onEvent, when input invalid password then validatePassword returns false`() {
        runBlocking(testDispatcher.default) {
            val passwordEvent = MainEvent.PasswordChanged("lkkl")

            val emailResult = ValidationResult(
                successful = false,
                errorMessage = "The email can not be blank"
            )
            coEvery { mockTextProvider.getText(any()) } returns emailResult.errorMessage.toString()
            coEvery { mockValidatePasswordUseCase.invoke(passwordEvent.password) } returns emailResult

            sut.onEvent(passwordEvent)

            assertFalse(sut.validatePassword())
        }
    }

    @Test
    fun `onEvent, when input valid PASSWORD then validatePassword returns true`() {
        runBlocking(testDispatcher.default) {
            val passwordEvent = MainEvent.PasswordChanged("Pkjkjkjkj12")

            val passwordResult = ValidationResult(
                successful = true,
                errorMessage = null
            )
            coEvery { mockTextProvider.getText(any()) } returns passwordResult.errorMessage.toString()
            coEvery { mockValidatePasswordUseCase.invoke(passwordEvent.password) } returns passwordResult

            sut.onEvent(passwordEvent)

            assertTrue(sut.validatePassword())
        }
    }
}

