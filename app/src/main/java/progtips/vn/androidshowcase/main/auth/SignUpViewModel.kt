package progtips.vn.androidshowcase.main.auth

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.content.repository.AuthRepository
import progtips.vn.sharedresource.helper.ValidateUtils
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

    fun signUp(email: String, password: String) {
        authRepository.signUp(email, password)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authRepository.onActivityResult(requestCode, resultCode, data)
    }
}