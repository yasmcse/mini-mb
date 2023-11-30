package com.moneybox.minimb.feature.login.usecase

import com.moneybox.minimb.common.TextProvider
import com.moneybox.minimb.common.utils.isPasswordValid
import com.moneybox.minimb.feature.login.R as loginR
import com.moneybox.minimb.feature.login.model.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(
    private val mbTextProvider:TextProvider
){
    operator fun invoke(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = mbTextProvider.getText(loginR.string.password_should_be_atleast_eight_chars)
            )
        }

        if (!isPasswordValid(password)) {
            return ValidationResult(
                successful = false,
                errorMessage = mbTextProvider.getText(loginR.string.password_should_contain_atleast_one_letter_and_digit)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}