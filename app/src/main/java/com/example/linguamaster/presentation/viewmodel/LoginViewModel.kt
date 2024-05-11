package com.example.linguamaster.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linguamaster.domain.model.RegistrationFormState
import com.example.linguamaster.domain.usecase.LoginByEmailUseCase
import com.example.linguamaster.domain.usecase.ValidateEmailUseCase
import com.example.linguamaster.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            try {
                loginByEmail.execute(registrationFormState.email, registrationFormState.password) {
                    if (it) {
                        Log.d("MyLog", "submitData: if $it")
                        successLoginLiveData.postValue(true)
                    } else {
                        Log.d("MyLog", "submitData: else $it")

                    }
                }
            } catch (e: Exception) {
                Log.d("MyLog", "submitData: if $e")
            }
        }
    }
}



