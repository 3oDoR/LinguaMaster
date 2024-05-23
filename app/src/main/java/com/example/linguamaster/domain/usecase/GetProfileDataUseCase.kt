package com.example.linguamaster.domain.usecase

import com.example.linguamaster.domain.model.ProfileData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor() {

    private val user = Firebase.auth.currentUser
    private val firebase = Firebase.database.reference.child("users").child(user!!.uid)
    fun execute(): ProfileData {
        return ProfileData(
            user?.photoUrl.toString(),
            user?.displayName.toString(),
            user?.email.toString(),
            "False",
            ""
        )
    }
}