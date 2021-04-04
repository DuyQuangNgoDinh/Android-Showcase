package progtips.vn.sharedresource.helper

import android.util.Patterns
import progtips.vn.sharedresource.helper.ValidateUtils.EmailValidation.*
import progtips.vn.sharedresource.helper.ValidateUtils.PasswordValidation.*

object ValidateUtils {
    /* Email validation */
    enum class EmailValidation {
        EmailEmpty,
        EmailInvalid,
        EmailValid
    }

    fun validateEmail(email: String?): EmailValidation {
        return when {
            email.isNullOrBlank() -> EmailEmpty
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> EmailInvalid
            else -> EmailValid
        }
    }

    /* Password validation */
    enum class PasswordValidation {
        PasswordEmpty,
        PasswordValid
    }

    fun validatePassword(password: String?): PasswordValidation {
        return when {
            password.isNullOrBlank() -> PasswordEmpty
            else -> PasswordValid
        }
    }
}