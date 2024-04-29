package com.example.linguamaster.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linguamaster.domain.model.RegistrationFormState
import com.example.linguamaster.domain.usecase.RegisterByEmailUseCase
import com.example.linguamaster.domain.usecase.ValidateConfirmPasswordUseCase
import com.example.linguamaster.domain.usecase.ValidateEmailUseCase
import com.example.linguamaster.domain.usecase.ValidatePasswordUseCase
import com.example.linguamaster.domain.usecase.ValidateUsernameUseCase

class RegisterViewModel(
    private val validateUsername: ValidateUsernameUseCase = ValidateUsernameUseCase(),
    private val validateEmail: ValidateEmailUseCase = ValidateEmailUseCase(),
    private val validatePassword: ValidatePasswordUseCase = ValidatePasswordUseCase(),
    private val validateConfirmPassword: ValidateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase(),
    private val registerByEmail: RegisterByEmailUseCase = RegisterByEmailUseCase()
) : ViewModel() {

    var usernameLiveData = MutableLiveData<String?>()
    var emailLiveData = MutableLiveData<String?>()
    var passwordLiveData = MutableLiveData<String?>()
    var confirmPasswordLiveData = MutableLiveData<String?>()
    var successRegisterLiveData = MutableLiveData<Boolean>()
    fun submitData(registrationFormState: RegistrationFormState) {
        val usernameResult = validateUsername.execute(registrationFormState.username)
        val emailResult = validateEmail.execute(registrationFormState.email)
        val passwordResult = validatePassword.execute(registrationFormState.password)
        val passwordConfirmResult = validateConfirmPassword.execute(
            registrationFormState.password,
            registrationFormState.confirmPassword
        )

        val hasError = listOf(
            usernameResult,
            emailResult,
            passwordResult,
            passwordConfirmResult
        ).any { !it.successful }

        if (hasError) {
            usernameLiveData.postValue(usernameResult.errorMessage)
            emailLiveData.postValue(emailResult.errorMessage)
            passwordLiveData.postValue(passwordResult.errorMessage)
            confirmPasswordLiveData.postValue(passwordConfirmResult.errorMessage)
            return
        }


        successRegisterLiveData.postValue(registerByEmail.execute(registrationFormState))
    }
}