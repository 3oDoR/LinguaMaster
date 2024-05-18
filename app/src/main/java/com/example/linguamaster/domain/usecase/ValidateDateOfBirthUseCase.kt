package com.example.linguamaster.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.linguamaster.domain.model.ValidationResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class ValidateDateOfBirthUseCase @Inject constructor() {


    @RequiresApi(Build.VERSION_CODES.O)
    fun execute(date: String): ValidationResult {
        if (date.isEmpty()) {
            return ValidationResult(
                false,
                "Empty blank"
            )
        }
        return if (isDateOfBirthValid(date)) {
            return ValidationResult(
                true,
                ""
            )
        } else {
            ValidationResult(
                false,
                "Date of Birth isn't valid. Example: dd.mm.yyyy"
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun isDateOfBirthValid(date: String): Boolean {
        val enteredDate = correctDateForPattern(date)
        if (enteredDate == "") {
            return false
        }
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val localDate = LocalDate.parse(enteredDate, formatter)
            !(!formatter.format(localDate).equals(enteredDate) || localDate > LocalDate.now())
        } catch (e: Exception) {
            false
        }
    }

    private fun correctDateForPattern(date: String): String {
        return try {
            val a = date.split(".")
            var day = a[0]
            var month = a[1]
            val year = a[2]
            if (day.length == 1) {
                day = "0$day"
            }
            if (month.length == 1) {
                month = "0$month"
            }
            "$day.$month.$year"
        } catch (e: Exception) {
            ""
        }
    }
}