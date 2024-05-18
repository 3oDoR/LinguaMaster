package com.example.linguamaster.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class SendPasswordResetEmailUseCase @Inject constructor() {
    fun execute(email: String) {
        val a = Firebase.auth.sendPasswordResetEmail(email)
    }


}