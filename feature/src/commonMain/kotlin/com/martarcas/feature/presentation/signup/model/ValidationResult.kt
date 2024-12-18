package com.martarcas.feature.presentation.signup.model

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String> = emptyList()
)