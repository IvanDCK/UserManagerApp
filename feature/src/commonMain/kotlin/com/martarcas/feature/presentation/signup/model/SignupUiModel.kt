package com.martarcas.feature.presentation.signup.model

data class SignupUiModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val avatarId: String
)