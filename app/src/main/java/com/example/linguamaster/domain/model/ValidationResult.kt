package com.example.linguamaster.domain.model

data class ValidationResult (
    var successful: Boolean = false,
    var errorMessage: String? = null
)
