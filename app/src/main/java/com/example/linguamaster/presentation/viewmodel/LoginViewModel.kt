package com.example.linguamaster.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linguamaster.domain.model.RegistrationFormState
import com.example.linguamaster.domain.usecase.login_and_register.LoginByEmailUseCase
import com.example.linguamaster.domain.usecase.login_and_register.ValidateEmailUseCase
import com.example.linguamaster.domain.usecase.login_and_register.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var validateEmailUseCase: ValidateEmailUseCase,
    private var validatePasswordUseCase: ValidatePasswordUseCase,
    private var loginByEmail: LoginByEmailUseCase,
) : ViewModel() {
    val emailLiveData = MutableLiveData<String?>()
    val passwordLiveData = MutableLiveData<String?>()
    val successLoginLiveData = MutableLiveData<Boolean>()
    suspend fun submitData(registrationFormState: RegistrationFormState) {
        val emailResult = validateEmailUseCase.execute(registrationFormState.email)
        val passwordResult = validatePasswordUseCase.execute(registrationFormState.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            emailLiveData.postValue(emailResult.errorMessage)
            passwordLiveData.postValue(passwordResult.errorMessage)
            return
        }
        viewModelScope.launch {
            loginByEmail.execute(registrationFormState.email, registrationFormState.password) {
                if (it.successful) {
                    successLoginLiveData.postValue(true)
                } else {
                    passwordLiveData.postValue(it.errorMessage)
                }
            }
        }
    }
}



