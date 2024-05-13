package com.example.linguamaster.domain.usecase.profile

import com.example.linguamaster.domain.model.ProfileData
import com.google.gson.Gson
import javax.inject.Inject

class DataToJsonUseCase @Inject constructor() {
    fun execute(profileData: ProfileData?): String? {
        return Gson().toJson(profileData)
    }
}