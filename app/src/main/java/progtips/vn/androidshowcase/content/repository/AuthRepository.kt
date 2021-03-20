package progtips.vn.androidshowcase.content.repository

import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import progtips.vn.androidshowcase.main.auth.model.AuthState
import progtips.vn.androidshowcase.main.auth.model.AuthState.Authenticated
import progtips.vn.androidshowcase.main.auth.model.AuthState.Unauthenticated
import progtips.vn.androidshowcase.utils.vmevent.Event
import progtips.vn.asia.authfirebase.AuthStatus
import progtips.vn.asia.authfirebase.FirebaseAuthManager
import progtips.vn.asia.authfirebase.account.LoginMethod
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authManager: FirebaseAuthManager
) {
    val authStateFlow = authManager.authStateFlow.map {
        currentAuthenticationState(it)
    }.onStart {
        currentAuthenticationState(null)
    }

    val loadingFlow = authManager.authLoadingFlow

    val errorFlow = authManager.authErrorFlow.map { Event(it) }

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

    private fun currentAuthenticationState(authStatus: AuthStatus?): AuthState {
        val user = FirebaseAuthManager.getCurrentUser(authStatus)
        return if (user != null) Authenticated(user) else Unauthenticated
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authManager.onGoogleLoginActivityResult(requestCode, resultCode, data)
        authManager.onFacebookLoginActivityResult(requestCode, resultCode, data)
    }
}