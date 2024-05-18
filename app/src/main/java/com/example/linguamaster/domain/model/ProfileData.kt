package com.example.linguamaster.domain.model

import javax.inject.Singleton

@Singleton
data class ProfileData(
    val uriAvatar: String = "",
    val username: String = "",
    val oldEmail: String = "",
    val newEmail: String = "",
    val oldPassword: String = "",
    val newPassword: String = "",
    val premium: String = "False",
    val dateOfBirth: String = "",

    )
