package com.martarcas.feature.signup.model

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String> = emptyList()
)