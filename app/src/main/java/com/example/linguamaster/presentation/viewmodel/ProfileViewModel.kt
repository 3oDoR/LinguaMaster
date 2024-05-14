package com.example.linguamaster.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linguamaster.domain.model.ProfileData
import com.example.linguamaster.domain.usecase.DataToJsonUseCase
import com.example.linguamaster.domain.usecase.GetProfileDataUseCase
import com.example.linguamaster.domain.usecase.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val dataToJsonUseCase: DataToJsonUseCase,
) : ViewModel() {

    val profileDataStateLiveData = MutableLiveData<ProfileData>()

    fun getProfileData() {
        profileDataStateLiveData.postValue(getProfileDataUseCase.execute())
    }

    fun logOut() {
        logOutUseCase.execute()
    }

    fun dataToJson(profileData: ProfileData?): String? {
        return dataToJsonUseCase.execute(profileData)
    }

}