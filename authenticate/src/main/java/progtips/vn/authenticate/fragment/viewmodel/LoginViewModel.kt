package progtips.vn.authenticate.fragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import progtips.vn.authenticate.fragment.manager.AuthManager
import progtips.vn.authenticate.fragment.manager.LoginMethod

class LoginViewModel: ViewModel() {
    enum class AuthenticateState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED  ,        // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    private lateinit var authManager: AuthManager

    val authenticateState = MutableLiveData<AuthenticateState>()
    var username = ""

    init {
        authenticateState.value = AuthenticateState.UNAUTHENTICATED
    }

    fun refuseAuthentication() {
        authenticateState.value = AuthenticateState.UNAUTHENTICATED
    }

    fun authenticate() {
        authManager.getLoginMethod(LoginMethod.Google)?.signIn()
    }
}