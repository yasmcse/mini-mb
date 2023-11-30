package com.moneybox.minimb.feature.login.usecase

import com.moneybox.minimb.common.TextProvider
import com.moneybox.minimb.common.utils.isEmailValid
import com.moneybox.minimb.feature.login.R as loginR
import com.moneybox.minimb.feature.login.model.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val mbTextProvider: TextProvider
) {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = mbTextProvider.getText(loginR.string.email_cant_be_blank)
            )
        }
        if (!isEmailValid(email)) {
            return ValidationResult(
                successful = false,
                errorMessage = mbTextProvider.getText(loginR.string.not_a_valid_email)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}