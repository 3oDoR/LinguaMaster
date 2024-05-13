package com.example.linguamaster.domain.usecase.edit_profile

import com.example.linguamaster.domain.model.ProfileData
import com.google.gson.Gson
import javax.inject.Inject

class ReplaceStringToClass @Inject constructor() {

    fun execute(profileData: String): ProfileData? {
        return Gson().fromJson(profileData, ProfileData::class.java)
    }


}