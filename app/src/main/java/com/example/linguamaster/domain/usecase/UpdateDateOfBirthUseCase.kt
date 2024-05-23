package com.example.linguamaster.domain.usecase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class UpdateDateOfBirthUseCase @Inject constructor() {
    private val userId = Firebase.auth.currentUser!!.uid
    private val database = FirebaseDatabase.getInstance().getReference("Users/$userId")
    private val db = Firebase.firestore

    fun execute(newDate: String) {
        val userData: MutableMap<String, String> = HashMap()
        userData["date"] = newDate
        try {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { result ->
                    if (newDate.isBlank() || newDate == (result.data?.get("date"))) {
                        return@addOnSuccessListener
                    }
                    db.collection("users").document(userId).set(userData)
                }
                .addOnFailureListener { exception ->
                    Log.w("MyLog", "Error getting documents: ", exception)
                }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}