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
    is AuthStatus.AuthStatusSuccess.SuccessEmailLogin -> LoginMethod.Email
    is AuthStatus.AuthStatusSuccess.SuccessGoogleLogin -> LoginMethod.Google
    is AuthStatus.AuthStatusSuccess.SuccessFacebookLogin -> LoginMethod.Facebook
    else -> null
}