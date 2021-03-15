package progtips.vn.asia.authfirebase.state

import android.content.Intent
import androidx.fragment.app.Fragment

interface AuthManagerState {
    fun login(email: String, password: String)

    fun createAccount(email: String, password: String)

    fun logout()

    fun loginWithGoogle(fragment: Fragment)

    fun loginWithFacebook(fragment: Fragment)

    fun onFacebookLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun onGoogleLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}