package progtips.vn.androidshowcase.main.auth

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import progtips.vn.androidshowcase.content.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    fun isInProgress() = authRepository.loadingFlow.asLiveData()

    fun getError() = authRepository.errorFlow.asLiveData()

    fun loginWithEmailPassword(email: String, password: String) {
        authRepository.login(email, password)
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