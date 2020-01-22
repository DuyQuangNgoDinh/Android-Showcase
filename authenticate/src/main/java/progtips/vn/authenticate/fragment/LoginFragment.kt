package progtips.vn.authenticate.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_social_login.*

import progtips.vn.authenticate.R
import progtips.vn.authenticate.fragment.entity.UserInfo
import progtips.vn.authenticate.fragment.manager.*
import java.util.concurrent.Executor

/**
 * Login with Google: https://developers.google.com/identity/sign-in/android/start-integrating
 * Login with Facebook: https://developers.facebook.com/docs/facebook-login/android/
 */
class LoginFragment : Fragment() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var authManager: AuthManager

    companion object {
        const val RC_SIGN_IN = 10

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
        btnGoogleLogin.setOnClickListener { authManager.getLoginMethod(LoginMethod.google)?.signIn() }

        biometricLogin()

        btnFacebookLogin.setOnClickListener { authManager.getLoginMethod(LoginMethod.facebook)?.signIn() }
    }

    override fun onStart() {
        super.onStart()

        authManager.checkUserAlreadyLoggedIn(
            {
                updateUI(it)
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (authManager.getLoginMethod(LoginMethod.facebook) as? FacebookAuth)?.callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            RC_SIGN_IN -> {
                (authManager.getLoginMethod(LoginMethod.google) as? GoogleAuth)?.getSignedInAccountFromIntent(data)
            }
        }
    }

    private fun updateUI(userInfo: UserInfo) {
        findNavController().popBackStack()
    }

    /**
     * Biometric Login
     */
    private fun biometricLogin() {
        executor = ContextCompat.getMainExecutor(context)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(context,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        btnFingerprintLogin.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}
