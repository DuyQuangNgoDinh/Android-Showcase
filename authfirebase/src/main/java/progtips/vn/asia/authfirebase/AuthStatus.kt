package progtips.vn.asia.authfirebase

import progtips.vn.asia.authfirebase.account.Account

sealed class AuthStatus {
    object Uninitialized: AuthStatus()
    object Authenticating: AuthStatus()
    class SuccessEmailLogin(account: Account?): AuthStatus()
    class SuccessSignUp(account: Account?): AuthStatus()
    class SuccessGoogleLogin(account: Account?): AuthStatus()
    class SuccessFacebookLogin(account: Account?): AuthStatus()
    object SuccessLogout: AuthStatus()
    object CancelGoogleLogin: AuthStatus()
    object CancelFacebookLogin: AuthStatus()
    class ErrorEmailLogin(message: String?): AuthStatus()
    class ErrorGoogleLogIn(message: String?): AuthStatus()
    class ErrorFacebookLogin(message: String?): AuthStatus()
    class ErrorSignUp(message: String?): AuthStatus()
}