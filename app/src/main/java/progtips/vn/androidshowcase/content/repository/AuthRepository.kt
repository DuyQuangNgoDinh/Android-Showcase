package progtips.vn.androidshowcase.content.repository

import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import progtips.vn.androidshowcase.main.auth.model.AuthState
import progtips.vn.androidshowcase.main.auth.model.AuthState.Authenticated
import progtips.vn.androidshowcase.main.auth.model.AuthState.Unauthenticated
import progtips.vn.asia.authfirebase.AuthStatus
import progtips.vn.asia.authfirebase.FirebaseAuthManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authManager: FirebaseAuthManager
) {
    val authStateFlow = authManager.authStateFlow.map {
        currentAuthenticationState()
    }.onStart {
        currentAuthenticationState()
    }

    val loadingFlow = authManager.authStateFlow.map { it == AuthStatus.Authenticating }

    fun login(email: String, password: String) {
        authManager.login(email, password)
    }

    fun signUp(email: String, password: String) {
        authManager.createAccount(email, password)
    }

    fun loginWithFacebook(fragment: Fragment) {
        authManager.loginWithFacebook(fragment)
    }

    fun loginWithGoogle(fragment: Fragment) {
        authManager.loginWithGoogle(fragment)
    }

    fun logout() {
        authManager.logout()
    }

    private fun currentAuthenticationState(): AuthState {
        val user = FirebaseAuthManager.getCurrentUser()
        return if (user != null) Authenticated(user) else Unauthenticated
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authManager.onGoogleLoginActivityResult(requestCode, resultCode, data)
        authManager.onFacebookLoginActivityResult(requestCode, resultCode, data)
    }
}