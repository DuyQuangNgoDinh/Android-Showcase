package progtips.vn.authenticate.fragment.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_social_login.*

import progtips.vn.authenticate.R
import progtips.vn.authenticate.fragment.entity.UserInfo
import progtips.vn.authenticate.fragment.manager.*

/**
 * Login with Google: https://developers.google.com/identity/sign-in/android/start-integrating
 * Login with Facebook: https://developers.facebook.com/docs/facebook-login/android/
 * Login with Biometric:
 */
class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var authManager: AuthManager

    companion object {
        const val GG_SIGN_IN = 10

        const val EMAIL = "email"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authManager = AuthManager(this)
        btnGoogleLogin.setOnClickListener(this)
        btnFingerprintLogin.setOnClickListener(this)
        btnFacebookLogin.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        authManager.checkUserAlreadyLoggedIn(
            {
                updateUI(it)
            }
        )
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btnGoogleLogin -> authManager.getLoginMethod(LoginMethod.Google)?.signIn()
            R.id.btnFacebookLogin -> authManager.getLoginMethod(LoginMethod.Facebook)?.signIn()
            R.id.btnFingerprintLogin -> authManager.getLoginMethod(LoginMethod.Biometric)?.signIn()
            R.id.btnSignIn ->
                (authManager.getLoginMethod(LoginMethod.Email) as? EmailAuth)?.signIn(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (authManager.getLoginMethod(LoginMethod.Facebook) as? FacebookAuth)?.callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            GG_SIGN_IN -> {
                (authManager.getLoginMethod(LoginMethod.Google) as? GoogleAuth)?.getSignedInAccountFromIntent(data)
            }
        }
    }

    private fun updateUI(userInfo: UserInfo) {
        findNavController().popBackStack()
    }
}
