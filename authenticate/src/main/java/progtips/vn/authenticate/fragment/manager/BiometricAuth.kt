package progtips.vn.authenticate.fragment.manager

import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import progtips.vn.authenticate.fragment.entity.UserInfo
import java.util.concurrent.Executor

class BiometricAuth(fragment: Fragment): AuthMethod(fragment) {
    private var executor: Executor
    private var biometricPrompt: BiometricPrompt
    private var promptInfo: BiometricPrompt.PromptInfo

    init {
        executor = ContextCompat.getMainExecutor(fragment.requireContext())

        biometricPrompt = BiometricPrompt(fragment, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(fragment.context,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(fragment.context,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(fragment.context, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()
    }

    override fun alreadyLoggedIn(): Boolean {
        return false
    }

    override fun signIn() {
        biometricPrompt.authenticate(promptInfo)
    }

    override fun getProfile(): UserInfo {
        return UserInfo("", "")
    }

    override fun signOut() {
        onSuccessSignOut()
    }
}