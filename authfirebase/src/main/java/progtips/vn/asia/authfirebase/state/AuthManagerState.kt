package progtips.vn.asia.authfirebase.state

import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import progtips.vn.asia.authfirebase.auth.AuthStatus

abstract class AuthManagerState {
    val authStateChannel = ConflatedBroadcastChannel<AuthStatus>(AuthStatus.Uninitialized)

    open fun login(email: String, password: String) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    open fun createAccount(email: String, password: String) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    open fun logout() {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    open fun loginWithGoogle(fragment: Fragment) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    open fun loginWithFacebook(fragment: Fragment) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    open fun onFacebookLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    open fun onGoogleLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }
}