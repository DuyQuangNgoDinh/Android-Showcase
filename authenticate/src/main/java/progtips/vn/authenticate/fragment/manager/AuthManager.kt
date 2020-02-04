package progtips.vn.authenticate.fragment.manager

import androidx.fragment.app.Fragment
import progtips.vn.authenticate.fragment.entity.UserInfo

class AuthManager(fragment: Fragment) {
    private var loginMethod = mapOf(
        LoginMethod.Google to GoogleAuth(fragment),
        LoginMethod.Facebook to FacebookAuth(fragment),
        LoginMethod.Email to EmailAuth(fragment),
        LoginMethod.Biometric to BiometricAuth(fragment)
    )

    val currentLoginMethod: AuthMethod?
    get() {
        loginMethod.forEach {
            if (it.value.alreadyLoggedIn())
                return it.value
        }

        return null
    }

    fun getLoginMethod(method: LoginMethod) = loginMethod[method]

    fun checkUserAlreadyLoggedIn(
        actionWhenLoggedIn: (UserInfo) -> Unit,
        actionWhenNotLoggedIn: (() -> Unit)? = null
    ) {
        currentLoginMethod?.getProfile()?.let {
            actionWhenLoggedIn(it)
            return
        }

        actionWhenNotLoggedIn?.invoke()
    }
}