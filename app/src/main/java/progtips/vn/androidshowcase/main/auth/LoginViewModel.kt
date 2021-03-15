package progtips.vn.androidshowcase.main.auth

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import progtips.vn.androidshowcase.BaseViewModel
import progtips.vn.androidshowcase.content.repository.AuthRepository
import progtips.vn.androidshowcase.main.auth.model.LoginModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): BaseViewModel() {
    private val _loginModelLiveData = MutableLiveData<LoginModel>()
    val loginModelLiveData: LiveData<LoginModel> = _loginModelLiveData

    val loadingLiveData = authRepository.loadingFlow.asLiveData()

    private var loginModel = LoginModel()

    fun loginWithEmailPassword() {
        authRepository.login(loginModel.email, loginModel.password)
    }

    fun loginWithGoogle(fragment: Fragment) = launch {
        authRepository.loginWithGoogle(fragment)
    }

    fun loginWithFacebook(fragment: Fragment) {
        authRepository.loginWithFacebook(fragment)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authRepository.onActivityResult(requestCode, resultCode, data)
    }
}