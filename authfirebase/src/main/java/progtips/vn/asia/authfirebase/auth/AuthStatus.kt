package progtips.vn.asia.authfirebase.auth

enum class AuthStatus {
    Uninitialized,
    SuccessEmailLogin,
    SuccessSignUp,
    SuccessGoogleLogin,
    SuccessFacebookLogin,
    SuccessLogout,
    CancelGoogleLogin,
    CancelFacebookLogin,
    ErrorEmailLogin,
    ErrorGoogleLogIn,
    ErrorFacebookLogin,
    ErrorSignUp
}