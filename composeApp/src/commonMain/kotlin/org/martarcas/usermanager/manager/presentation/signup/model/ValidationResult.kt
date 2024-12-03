package org.martarcas.usermanager.manager.presentation.signup.model

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String> = emptyList()
)