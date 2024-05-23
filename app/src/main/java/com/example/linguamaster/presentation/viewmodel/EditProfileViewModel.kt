package com.example.linguamaster.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linguamaster.domain.model.ProfileData
import com.example.linguamaster.domain.usecase.ReplaceStringToClass
import com.example.linguamaster.domain.usecase.UpdateDateOfBirthUseCase
import com.example.linguamaster.domain.usecase.UpdateEmailUseCase
import com.example.linguamaster.domain.usecase.UpdateUsernameUseCase
import com.example.linguamaster.domain.usecase.ValidateDateOfBirthUseCase
import com.example.linguamaster.domain.usecase.ValidateEmailUseCase
import com.example.linguamaster.domain.usecase.ValidateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val replaceStringToClass: ReplaceStringToClass,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateDateOfBirthUseCase: ValidateDateOfBirthUseCase,
    private val updateUsernameUseCase: UpdateUsernameUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val updateDateOfBirthUseCase: UpdateDateOfBirthUseCase
) : ViewModel() {

    val profileBanksStateLiveData = MutableLiveData<ProfileData>()
    var usernameLiveData = MutableLiveData<String?>()
    var emailLiveData = MutableLiveData<String?>()
    var dateOfBirthLiveData = MutableLiveData<String?>()
    var successUpdateLiveData = MutableLiveData<Boolean>()

    fun replaceStringToClass(profileData: String) {
        profileBanksStateLiveData.postValue(replaceStringToClass.execute(profileData))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData(profileData: ProfileData) {
        val usernameResult = validateUsernameUseCase.execute(profileData.username)
        val emailResult = validateEmailUseCase.execute(profileData.oldEmail)
        val dateOfBirthResult = validateDateOfBirthUseCase.execute(profileData.dateOfBirth)

        val hasError = listOf(
            usernameResult,
            emailResult,
            dateOfBirthResult
        ).any { !it.successful }

        if (hasError) {
            usernameLiveData.postValue(usernameResult.errorMessage)
            emailLiveData.postValue(emailResult.errorMessage)
            dateOfBirthLiveData.postValue(dateOfBirthResult.errorMessage)
            return
        }

        updateUsernameUseCase.execute(profileData.username)
        updateEmailUseCase.execute(
            profileData.oldEmail,
            profileData.oldPassword,
            profileData.newEmail
        )

        updateDateOfBirthUseCase.execute(profileData.dateOfBirth)
        successUpdateLiveData.postValue(true)

    }
}