package com.example.linguamaster.domain.usecase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UpdateDateOfBirthUseCase @Inject constructor() {
    private val database = Firebase.database.reference.child("users").child("Date Of Birth")
    fun execute(newDate: String) {
        Log.d("MyLog", "execute: ${database.child(Firebase.auth.currentUser!!.uid).get().result}")
        if (newDate.isBlank() || newDate == database.get().result.value) {
            Log.d("MyLog", "execute: ret")
            return
        }
        Log.d("MyLog", "execute: not ret")
        database.child("users")
            .child(Firebase.auth.currentUser!!.uid)
            .child("Date Of Birth")
            .push().setValue(newDate)
    }
}