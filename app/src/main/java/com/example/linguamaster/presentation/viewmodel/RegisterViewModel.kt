package com.example.linguamaster.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linguamaster.domain.model.RegistrationFormState
import com.example.linguamaster.domain.usecase.login_and_register.RegisterByEmailUseCase
import com.example.linguamaster.domain.usecase.login_and_register.ValidateConfirmPasswordUseCase
import com.example.linguamaster.domain.usecase.login_and_register.ValidateEmailUseCase
import com.example.linguamaster.domain.usecase.login_and_register.ValidatePasswordUseCase
import com.example.linguamaster.domain.usecase.login_and_register.ValidateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateUsername: ValidateUsernameUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateConfirmPassword: ValidateConfirmPasswordUseCase,
    private val registerByEmail: RegisterByEmailUseCase
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