package com.example.linguamaster.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linguamaster.domain.model.RegistrationFormState
import com.example.linguamaster.domain.usecase.LoginByEmailUseCase
import com.example.linguamaster.domain.usecase.ValidateEmailUseCase
import com.example.linguamaster.domain.usecase.ValidatePasswordUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase = ValidateEmailUseCase(),
    private val validatePasswordUseCase: ValidatePasswordUseCase = ValidatePasswordUseCase(),
    private val loginByEmail: LoginByEmailUseCase = LoginByEmailUseCase()

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


