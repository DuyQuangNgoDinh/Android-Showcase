package progtips.vn.asia.authfirebase.account

import com.google.firebase.auth.FirebaseUser
import progtips.vn.asia.authfirebase.AuthStatus

internal fun FirebaseUser.toAccount(authStatus: AuthStatus? = null) = Account(
    uid,
    displayName,
    email,
    photoUrl.toString(),
    providerId,
    authStatus?.toLoginMethod()
)

private fun AuthStatus.toLoginMethod(): LoginMethod? = when(this) {
    is AuthStatus.SuccessEmailLogin -> LoginMethod.Email
    is AuthStatus.SuccessGoogleLogin -> LoginMethod.Google
    is AuthStatus.SuccessFacebookLogin -> LoginMethod.Facebook
    else -> null
}