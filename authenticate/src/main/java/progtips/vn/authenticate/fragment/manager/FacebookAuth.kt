package progtips.vn.authenticate.fragment.manager

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.AccessToken
import progtips.vn.authenticate.R
import progtips.vn.authenticate.fragment.entity.UserInfo


class FacebookAuth(
    private val fragment: Fragment
): AuthMethod(fragment) {
    var callbackManager: CallbackManager? = null

    private val facebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            val userInfo = getProfile()
            onSuccessSignIn(userInfo)
        }

        override fun onCancel() {}

        override fun onError(error: FacebookException) {
//            showErrorAlert(error.localizedMessage)
        }
    }

    init {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback)
    }

    override fun alreadyLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

    override fun getProfile(): UserInfo {
        val fbProfile = Profile.getCurrentProfile()
        return UserInfo("", fbProfile.name)
    }

    override fun signIn() {
        LoginManager.getInstance()
            .logInWithReadPermissions(fragment, listOf("public_profile"))
    }

    override fun signOut() {
        LoginManager.getInstance().logOut()
        onSuccessSignOut()
    }
}