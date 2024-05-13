package com.example.linguamaster.domain.usecase.login_and_register

import android.net.Uri
import com.example.linguamaster.domain.model.RegistrationFormState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class RegisterByEmailUseCase @Inject constructor() {

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

