package com.example.linguamaster.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginByEmailUseCase @Inject constructor() {

    suspend fun execute(
        email: String,
        password: String,
        onComplete: (ValidationResult) -> Unit,
    ): Any = withContext(Dispatchers.IO) {
        try {
            Firebase.auth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onComplete.invoke(ValidationResult(true, null))
                    } else {
                        onComplete.invoke(ValidationResult(false, "Email or password is incorrect"))
                    }
                }.await()
        } catch (e: Exception) {
            onComplete.invoke(ValidationResult(false, "Email or password is incorrect"))
        }
    }
}
