package com.example.linguamaster.domain.usecase.login_and_register

import android.util.Patterns
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That isn't a valid email"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}