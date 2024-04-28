package com.example.linguamaster.domain.usecase

class ValidateUsernameUseCase {

    fun execute(username: String): ValidationResult {
        if (username.length < 4) {
            return ValidationResult(
                successful = false,
                errorMessage = "The username can't be less than 4 characters"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}