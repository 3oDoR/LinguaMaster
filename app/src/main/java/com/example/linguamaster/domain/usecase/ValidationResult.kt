package com.example.linguamaster.domain.usecase

data class ValidationResult (
    var successful: Boolean = false,
    var errorMessage: String? = null
)
