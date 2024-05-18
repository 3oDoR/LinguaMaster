package com.example.linguamaster.domain.usecase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UpdateUsernameUseCase @Inject constructor() {
    private val user = Firebase.auth.currentUser
    fun execute(enteredUsername: String) {
        if (user?.displayName == enteredUsername || enteredUsername.isBlank()) {
            return
        } else {
            val profileUpdates = userProfileChangeRequest {
                displayName = enteredUsername
            }
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("MyLog", "User profile updated.")
                    }
                }
        }
    }
}