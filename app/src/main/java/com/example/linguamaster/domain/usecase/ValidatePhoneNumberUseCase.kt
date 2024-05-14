package com.example.linguamaster.domain.usecase

import android.telephony.PhoneNumberUtils
import com.example.linguamaster.domain.model.ValidationResult
import javax.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor() {

    fun execute(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                false,
                "Phone number can't be blank"
            )
        }
        if (phoneNumber.length < 13) {
            return if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
                ValidationResult(
                    false,
                    "Current phone number isn't correct"
                )
            } else {
                ValidationResult(
                    true,
                    ""
                )
            }
        }
        return ValidationResult(
            false,
            "Current phone number longer than 12 numbers"
        )
    }
}