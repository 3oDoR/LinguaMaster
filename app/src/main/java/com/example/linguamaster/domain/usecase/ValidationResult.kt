package com.example.linguamaster.domain.usecase

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
