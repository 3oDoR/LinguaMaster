package com.example.linguamaster.domain.model

import javax.inject.Singleton

@Singleton
data class ProfileData(
    val uriAvatar: String = "",
    val username: String = "",
    val email: String = "",
    val premium: String = "",
    val dateOfBirth: String = "",
    val currentPassword: String = "",
    val newPassword: String = ""
)
