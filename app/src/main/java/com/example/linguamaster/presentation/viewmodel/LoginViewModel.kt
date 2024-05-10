package com.example.linguamaster.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linguamaster.domain.model.RegistrationFormState
import com.example.linguamaster.domain.usecase.LoginByEmailUseCase
import com.example.linguamaster.domain.usecase.ValidateEmailUseCase
import com.example.linguamaster.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var validateEmailUseCase: ValidateEmailUseCase,
    private var validatePasswordUseCase: ValidatePasswordUseCase,
    private var loginByEmail: LoginByEmailUseCase
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
        runBlocking {
            async {
                val loginResult = loginByEmail.execute(
                    registrationFormState.email,
                    registrationFormState.password
                )
                if (!loginResult.successful) {
                    passwordLiveData.postValue(loginResult.errorMessage)
                    return@async
                }
                successLoginLiveData.postValue(true)
            }.await()
        }
    }
}


