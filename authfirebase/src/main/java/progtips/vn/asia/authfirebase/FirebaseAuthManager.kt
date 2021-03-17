package progtips.vn.asia.authfirebase

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import progtips.vn.asia.authfirebase.account.LoginMethod
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