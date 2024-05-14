package com.example.linguamaster.domain.usecase

import com.example.linguamaster.domain.model.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password can't be less than 8 characters"
            )
        }
        if (!(password.any { it.isDigit() } && password.any { it.isLetter() })) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs contains at least one letter and digit"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}