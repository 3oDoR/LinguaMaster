package com.example.linguamaster.domain.usecase

class ValidateConfirmPasswordUseCase {

    fun execute(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The confirm password can't be blank"
            )
        }
        if (confirmPassword.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The confirm password can't be less than 8 characters"
            )
        }
        if (!(confirmPassword.any { it.isDigit() } && confirmPassword.any { it.isLetter() })) {
            return ValidationResult(
                successful = false,
                errorMessage = "The confirm password needs contains at least one letter and digit"
            )
        }
        if (password != confirmPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}