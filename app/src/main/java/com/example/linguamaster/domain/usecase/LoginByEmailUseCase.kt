package com.example.linguamaster.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class LoginByEmailUseCase @Inject constructor() {
    private lateinit var auth: FirebaseAuth

    fun execute(email: String, password: String): ValidationResult {
        auth = Firebase.auth
        val signIn = auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    return@addOnCompleteListener
                }
            }
        if (signIn.isSuccessful) {
            return ValidationResult(
                successful = true,
                errorMessage = null
            )
        }
        return  ValidationResult(
            successful = false,
            errorMessage = "false"
        )
    }
}
