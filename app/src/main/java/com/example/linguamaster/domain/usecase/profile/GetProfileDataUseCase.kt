package com.example.linguamaster.domain.usecase.profile

import com.example.linguamaster.domain.model.ProfileData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor() {

    private val user = Firebase.auth.currentUser
    fun execute(): ProfileData {
        return ProfileData(
            user?.photoUrl.toString(),
            user?.displayName.toString(),
            user?.email.toString(),
            user?.phoneNumber.toString(),
            "Premium: " + false,
            ""
        )
    }


}