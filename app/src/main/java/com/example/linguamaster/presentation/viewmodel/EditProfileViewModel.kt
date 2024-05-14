package com.example.linguamaster.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linguamaster.domain.model.ProfileData
import com.example.linguamaster.domain.usecase.ReplaceStringToClass
import com.example.linguamaster.domain.usecase.ValidateDateOfBirthUseCase
import com.example.linguamaster.domain.usecase.ValidateEmailUseCase
import com.example.linguamaster.domain.usecase.ValidatePhoneNumberUseCase
import com.example.linguamaster.domain.usecase.ValidateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val replaceStringToClass: ReplaceStringToClass,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validateDateOfBirthUseCase: ValidateDateOfBirthUseCase,
) : ViewModel() {

    val profileBanksStateLiveData = MutableLiveData<ProfileData>()
    var usernameLiveData = MutableLiveData<String?>()
    var emailLiveData = MutableLiveData<String?>()
    var phoneNumberLiveData = MutableLiveData<String?>()
    var dateOfBirthLiveData = MutableLiveData<String?>()
    var successUpdateLiveData = MutableLiveData<Boolean>()
    fun replaceStringToClass(profileData: String) {
        profileBanksStateLiveData.postValue(replaceStringToClass.execute(profileData))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData(profileData: ProfileData) {
        val usernameResult = validateUsernameUseCase.execute(profileData.username)
        val emailResult = validateEmailUseCase.execute(profileData.email)
        val phoneNumberResult = validatePhoneNumberUseCase.execute(profileData.phoneNumber)
        val dateOfBirthResult = validateDateOfBirthUseCase.execute(profileData.dateOfBirth)

        val hasError = listOf(
            usernameResult,
            emailResult,
            phoneNumberResult,
            dateOfBirthResult
        ).any { !it.successful }

        if (hasError) {
            usernameLiveData.postValue(usernameResult.errorMessage)
            emailLiveData.postValue(emailResult.errorMessage)
            phoneNumberLiveData.postValue(phoneNumberResult.errorMessage)
            dateOfBirthLiveData.postValue(dateOfBirthResult.errorMessage)
            return
        }
        successUpdateLiveData.postValue(true)
    }
}