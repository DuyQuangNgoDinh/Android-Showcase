package progtips.vn.androidshowcase.main.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import progtips.vn.androidshowcase.databinding.FmLoginBinding

@Composable
fun LoginUI(
    onButtonLoginClick: () -> Unit,
    onLoginGoogleClick: () -> Unit,
    onFacebookLoginClick: () -> Unit
) {
    AndroidViewBinding(FmLoginBinding::inflate) {
        btnSignIn.setOnClickListener { onButtonLoginClick.invoke() }
        socialLogin.run {
            btnGoogleLogin.setOnClickListener { onLoginGoogleClick.invoke() }
            btnFacebookLogin.setOnClickListener { onFacebookLoginClick.invoke() }
        }
    }
}

@Preview
@Composable
fun PreviewLoginUI() {
    LoginUI({}, {}, {})
}