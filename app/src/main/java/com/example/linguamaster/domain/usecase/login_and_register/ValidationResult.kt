package com.example.linguamaster.domain.usecase.login_and_register

data class ValidationResult (
    var successful: Boolean = false,
    var errorMessage: String? = null
)
