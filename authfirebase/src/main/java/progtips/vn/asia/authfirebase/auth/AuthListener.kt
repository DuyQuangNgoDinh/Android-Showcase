package progtips.vn.asia.authfirebase.auth

import progtips.vn.asia.authfirebase.account.Account

interface AuthListener {
    fun onSuccess(status: AuthStatus, account: Account?)
    fun onCancel(status: AuthStatus)
    fun onError(status: AuthStatus, message: String?)
}