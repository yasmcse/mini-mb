package com.moneybox.minimb.feature.login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneybox.minimb.common.di.CommonModule
import com.moneybox.minimb.common.model.login.Login
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.feature.login.model.MainEvent
import com.moneybox.minimb.feature.login.model.MainState
import com.moneybox.minimb.feature.login.usecase.LoginUseCase
import com.moneybox.minimb.feature.login.usecase.ValidateEmailUseCase
import com.moneybox.minimb.feature.login.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    @CommonModule.Feature private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _loginState: MutableStateFlow<NetworkResult<Login>> =
        MutableStateFlow(NetworkResult.NoState)
    val loginState: StateFlow<NetworkResult<Login>> =
        _loginState.asStateFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NetworkResult.NoState
        )

    var formState by mutableStateOf(MainState())


    fun onEvent(event: MainEvent) {
        viewModelScope.launch(dispatcher.default) {
            when (event) {
                is MainEvent.EmailChanged -> {
                    formState = formState.copy(email = event.email)
                    validateEmail()
                }

                is MainEvent.PasswordChanged -> {
                    formState = formState.copy(password = event.password)
                    validatePassword()
                }

                is MainEvent.VisiblePassword -> {
                    formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
                }

                is MainEvent.Submit -> {
                    if (validateEmail() && validatePassword()) {
                        loginUseCase(formState.email, formState.password)
                            .collect {
                                _loginState.value = it
                            }
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase(formState.email)
        formState = formState.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }

    @VisibleForTesting
    fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase(formState.password)
        formState = formState.copy(passwordError = passwordResult.errorMessage)
        return passwordResult.successful
    }
}
