package progtips.vn.asia.authfirebase

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import progtips.vn.asia.authfirebase.account.toAccount
import progtips.vn.asia.authfirebase.auth.AuthListener
import progtips.vn.asia.authfirebase.state.AuthManagerInitializedState
import progtips.vn.asia.authfirebase.state.AuthManagerState
import progtips.vn.asia.authfirebase.state.AuthManagerUninitializedState

class FirebaseAuthManager {

    companion object {
        fun getCurrentUser() = FirebaseAuth.getInstance().currentUser?.toAccount()
    }

    private var authManagerState: AuthManagerState = AuthManagerUninitializedState()
    private var authListener: AuthListener? = null

    fun initialize(activity: Activity) {
        authManagerState = AuthManagerInitializedState(activity, authListener)
    }

    fun setAuthListener(listener: AuthListener) {
        this.authListener = listener
        authManagerState.setAuthListener(listener)
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