package com.example.linguamaster.domain.usecase

import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import com.example.linguamaster.domain.model.RegistrationFormState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class RegisterByEmailUseCase {

    private lateinit var auth: FirebaseAuth
    fun execute(registrationFormState: RegistrationFormState): Boolean {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(
            registrationFormState.email,
            registrationFormState.password
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = registrationFormState.username
                        photoUri =
                            Uri.parse("https://storage.needpix.com/rsynced_images/avatar-1292817_1280.png")
                    }
                    auth.currentUser?.updateProfile(profileUpdates)
                }
            }
        return auth.currentUser != null
    }
}

