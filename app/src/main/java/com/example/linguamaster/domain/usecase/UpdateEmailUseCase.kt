package com.example.linguamaster.domain.usecase

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class UpdateEmailUseCase @Inject constructor() {

    private val user = Firebase.auth.currentUser

    fun execute(oldEmail: String, password: String, enteredEmail: String) {
        Log.d("MyLog", "IN")
        if (user?.email == enteredEmail || enteredEmail.isBlank()) {
            return
        }
        Log.d("MyLog", "After if")
        val credential = EmailAuthProvider.getCredential(oldEmail, password)
        user!!.reauthenticate(credential)
            .addOnCompleteListener {
                Log.d("MyLog", "User re-authenticated.")
                val user = FirebaseAuth.getInstance().currentUser
                user!!.updateEmail(enteredEmail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("MyLog", "User email address updated.")
                        } else {
                            Log.d("MyLog", task.exception.toString())
                        }
                        Log.d("MyLog", "Test")
                    }
            }
    }
}