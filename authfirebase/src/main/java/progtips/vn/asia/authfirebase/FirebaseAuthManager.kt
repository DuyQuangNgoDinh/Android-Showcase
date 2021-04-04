package progtips.vn.asia.authfirebase

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import progtips.vn.asia.authfirebase.AuthStatus.*
import progtips.vn.asia.authfirebase.account.toAccount
import progtips.vn.asia.authfirebase.state.AuthManagerInitializedState
import progtips.vn.asia.authfirebase.state.AuthManagerState
import progtips.vn.asia.authfirebase.state.AuthManagerUninitializedState

/**
 * Login with Google: https://developers.google.com/identity/sign-in/android/start-integrating
 * Login with Facebook: https://developers.facebook.com/docs/facebook-login/android/
 * Login with Biometric:
 */
class FirebaseAuthManager {

    companion object {
        fun getCurrentUser(authStatus: AuthStatus? = null) = FirebaseAuth.getInstance().currentUser?.toAccount(authStatus)
    }

    private val authStateChannel = ConflatedBroadcastChannel<AuthStatus>(AuthStatus.Uninitialized)

    val authStateFlow = authStateChannel.asFlow()

    val authSuccessFlow = authStateFlow.filter {
        it is AuthStatusSuccess
    }

    val authLoadingFlow = authStateFlow.map { it == Authenticating }

    val authCancelFlow = authStateFlow.filter { it is AuthStatusCancel }

    val authErrorFlow: Flow<AuthStatusError> = authStateFlow.filter { it is AuthStatusError } as Flow<AuthStatusError>

    private var authManagerState: AuthManagerState = AuthManagerUninitializedState(authStateChannel)

    fun initialize(activity: Activity) {
        authManagerState = AuthManagerInitializedState(activity, authStateChannel)
    }

    fun login(email: String, password: String) {
        authManagerState.login(email, password)
    }

    fun createAccount(email: String, password: String) {
        authManagerState.createAccount(email, password)
    }

    fun logout() {
        authManagerState.logout()
    }

    fun loginWithGoogle(fragment: Fragment) {
        authManagerState.loginWithGoogle(fragment)
    }

    fun loginWithFacebook(fragment: Fragment) {
        authManagerState.loginWithFacebook(fragment)
    }

    fun onFacebookLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authManagerState.onFacebookLoginActivityResult(requestCode, resultCode, data)
    }

    fun onGoogleLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authManagerState.onGoogleLoginActivityResult(requestCode, resultCode, data)
    }
}