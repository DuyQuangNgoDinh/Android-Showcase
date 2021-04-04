package progtips.vn.asia.authfirebase

import progtips.vn.asia.authfirebase.account.Account

sealed class AuthStatus {
    object Uninitialized: AuthStatus()
    object Authenticating: AuthStatus()

    sealed class AuthStatusSuccess: AuthStatus() {
        class SuccessEmailLogin(account: Account?): AuthStatusSuccess()
        class SuccessSignUp(account: Account?): AuthStatusSuccess()
        class SuccessGoogleLogin(account: Account?): AuthStatusSuccess()
        class SuccessFacebookLogin(account: Account?): AuthStatusSuccess()
        object SuccessLogout: AuthStatusSuccess()
    }

    sealed class AuthStatusCancel: AuthStatus() {
        object CancelGoogleLogin: AuthStatusCancel()
        object CancelFacebookLogin: AuthStatusCancel()
    }

    sealed class AuthStatusError(val message: String?): AuthStatus() {
        class ErrorEmailLogin(message: String?): AuthStatusError(message)
        class ErrorGoogleLogIn(message: String?): AuthStatusError(message)
        class ErrorFacebookLogin(message: String?): AuthStatusError(message)
        class ErrorSignUp(message: String?): AuthStatusError(message)
    }
}