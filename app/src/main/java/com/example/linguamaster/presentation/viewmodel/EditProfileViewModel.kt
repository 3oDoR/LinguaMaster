package com.example.linguamaster.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linguamaster.domain.model.ProfileData
import com.example.linguamaster.domain.usecase.edit_profile.ReplaceStringToClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val replaceStringToClass: ReplaceStringToClass,
) : ViewModel() {

    val profileBanksLiveData = MutableLiveData<ProfileData>()
    fun replaceStringToClass(profileData: String) {
        profileBanksLiveData.postValue(replaceStringToClass.execute(profileData))
    }
}