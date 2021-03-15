package progtips.vn.asia.authfirebase.state

import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import progtips.vn.asia.authfirebase.auth.AuthStatus

class AuthManagerUninitializedState(private val authStateChannel: ConflatedBroadcastChannel<AuthStatus>): AuthManagerState {
    override fun login(email: String, password: String) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    override fun createAccount(email: String, password: String) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    override fun logout() {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    override fun loginWithGoogle(fragment: Fragment) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    override fun loginWithFacebook(fragment: Fragment) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    override fun onFacebookLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }

    override fun onGoogleLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authStateChannel.offer(AuthStatus.Uninitialized)
    }
}