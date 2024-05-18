package com.example.linguamaster.domain.usecase

import com.example.linguamaster.domain.model.ProfileData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor() {
    private val user = Firebase.auth.currentUser
    fun execute(profileData: ProfileData) {
        updateUserName(profileData)
        updateEmail(profileData)
    }

    private fun updateUserName(profileData: ProfileData) {
        val profileUpdates = userProfileChangeRequest {
            displayName = profileData.username
        }
        user?.updateProfile(profileUpdates)
    }

    private fun updateEmail(profileData: ProfileData) {
        user?.updateEmail(profileData.oldEmail)
    }
}