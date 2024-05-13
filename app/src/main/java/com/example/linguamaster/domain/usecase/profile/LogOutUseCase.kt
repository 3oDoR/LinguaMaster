package com.example.linguamaster.domain.usecase.profile

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class LogOutUseCase @Inject constructor() {

    fun execute() {
        Firebase.auth.signOut()
    }
}