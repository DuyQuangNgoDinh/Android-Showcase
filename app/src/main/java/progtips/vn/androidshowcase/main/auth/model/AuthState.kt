package progtips.vn.androidshowcase.main.auth.model

import progtips.vn.asia.authfirebase.account.Account

sealed class AuthState {
    data class Authenticated(val account: Account): AuthState()
    object Unauthenticated: AuthState()
    object UnknownAuthState: AuthState()
}