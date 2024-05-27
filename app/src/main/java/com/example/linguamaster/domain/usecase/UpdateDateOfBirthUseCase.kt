package com.example.linguamaster.domain.usecase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class UpdateDateOfBirthUseCase @Inject constructor() {

    private val userId = Firebase.auth.currentUser!!.uid
    private val db = Firebase.firestore

    fun execute(newDate: String) {

        db.collection("users").document(userId).get()
            .addOnSuccessListener {
                if (newDate.isBlank() || newDate == (it.data?.get("date"))) {
                    Log.d("MyLog", "New date equals old date or new date is empty. Date = $newDate")
                    return@addOnSuccessListener
                }
                db.collection("users").document(userId).set(mutableMapOf("date" to newDate))
                Log.d("MyLog", "New date success changed. New date = $newDate")
            }
            .addOnFailureListener { exception ->
                Log.w("MyLog", "Error getting documents: ", exception)
            }
    }
}