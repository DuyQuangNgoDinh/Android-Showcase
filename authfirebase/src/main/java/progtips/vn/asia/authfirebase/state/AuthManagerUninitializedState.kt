package progtips.vn.asia.authfirebase.state

import android.content.Intent
import androidx.fragment.app.Fragment
import progtips.vn.asia.authfirebase.auth.AuthListener
import progtips.vn.asia.authfirebase.auth.AuthStatus.Uninitialized

class AuthManagerUninitializedState: AuthManagerState {
    private var authListener: AuthListener? = null

    override fun setAuthListener(authListener: AuthListener) {
        this.authListener = authListener
    }

    override fun login(email: String, password: String) {
        authListener?.onError(Uninitialized, "Uninitialized")
    }

    override fun createAccount(email: String, password: String) {
        authListener?.onError(Uninitialized, "Uninitialized")
    }

    override fun logout() {
        authListener?.onError(Uninitialized, "Uninitialized")
    }

    override fun loginWithGoogle(fragment: Fragment) {
        authListener?.onError(Uninitialized, "Uninitialized")
    }

    override fun loginWithFacebook(fragment: Fragment) {
        authListener?.onError(Uninitialized, "Uninitialized")
    }

    override fun onFacebookLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authListener?.onError(Uninitialized, "Uninitialized")
    }

    override fun onGoogleLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        authListener?.onError(Uninitialized, "Uninitialized")
    }
}