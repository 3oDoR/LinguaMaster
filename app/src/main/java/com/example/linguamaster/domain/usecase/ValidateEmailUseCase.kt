package com.example.linguamaster.domain.usecase

import android.util.Patterns
import java.util.regex.Pattern

class ValidateEmailUseCase {

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
            successful = true
        )
    }
}