package progtips.vn.androidshowcase.main.auth

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.content.repository.AuthRepository
import progtips.vn.sharedresource.helper.ValidateUtils
import progtips.vn.sharedresource.helper.ValidateUtils.ConfirmPasswordValidation.*
import progtips.vn.sharedresource.helper.ValidateUtils.EmailValidation.*
import progtips.vn.sharedresource.helper.ValidateUtils.PasswordValidation.PasswordEmpty
import progtips.vn.sharedresource.helper.ValidateUtils.PasswordValidation.PasswordValid
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _validateEmailChannel = ConflatedBroadcastChannel<Throwable>()
    val validateEmailLiveData = _validateEmailChannel.asFlow().asLiveData()

    private val _validatePasswordChannel = ConflatedBroadcastChannel<Throwable>()
    val validatePasswordLiveData = _validatePasswordChannel.asFlow().asLiveData()

    private val _validateConfirmPasswordChannel = ConflatedBroadcastChannel<Throwable>()
    val validateConfirmPasswordLiveData = _validateConfirmPasswordChannel.asFlow().asLiveData()

    fun verifyEmail(context: Context, email: String?) {
        when(ValidateUtils.validateEmail(email)) {
            EmailEmpty -> context.getString(R.string.error_email_empty)
            EmailInvalid -> context.getString(R.string.error_email_invalid)
            EmailValid -> null
        }.also { errorMessage ->
            _validateEmailChannel.offer(Throwable(errorMessage))
        }
    }

    fun verifyPassword(context: Context, password: String?) {
        when(ValidateUtils.validatePassword(password)) {
            PasswordEmpty -> context.getString(R.string.error_password_empty)
            PasswordValid -> null
        }.also { errorMessage ->
            _validatePasswordChannel.offer(Throwable(errorMessage))
        }
    }

    fun verifyConfirmPassword(context: Context, confirmPassword: String?, password: String?) {
        when(ValidateUtils.validateConfirmPassword(confirmPassword, password)) {
            ConfirmPasswordEmpty -> context.getString(R.string.error_confirm_password_empty)
            ConfirmPasswordNotMatch -> context.getString(R.string.error_confirm_password_not_match)
            ConfirmPasswordValid -> null
        }?.also { errorMessage ->
            _validateConfirmPasswordChannel.offer(Throwable(errorMessage))
        }
    }

    fun signUp(email: String, password: String) {
        authRepository.signUp(email, password)
    }

    fun loginWithGoogle(fragment: Fragment) {
        authRepository.loginWithGoogle(fragment)
    }

    fun loginWithFacebook(fragment: Fragment) {
        authRepository.loginWithFacebook(fragment)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authRepository.onActivityResult(requestCode, resultCode, data)
    }
}