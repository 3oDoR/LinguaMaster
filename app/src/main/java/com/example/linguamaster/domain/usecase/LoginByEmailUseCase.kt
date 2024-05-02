package com.example.linguamaster.domain.usecase

import android.util.Log
import android.view.View
import androidx.fragment.app.commit
import com.example.linguamaster.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class LoginByEmailUseCase {
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
