package progtips.vn.authenticate.fragment.manager

import android.content.Context
import androidx.fragment.app.Fragment
import progtips.vn.authenticate.fragment.entity.UserInfo

class EmailAuth(private val fragment: Fragment): AuthMethod(fragment) {
    private var email: String = ""
    private var password: String = ""

    companion object {
        const val EMAIL_LOGIN = "EMAIL_LOGIN"
    }

    override fun alreadyLoggedIn(): Boolean {
        val sharedPref = fragment.activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
        val account = sharedPref.getString(EMAIL_LOGIN, "")
        if (account.isNullOrEmpty()) return false
        return true
    }

    fun signIn(email: String, password: String) {
        this.email = email
        this.password = password
        signIn()
    }

    override fun signIn() {
        if (email == "nddquang" && password == "123456") {
            val sharedPref = fragment.activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString(EMAIL_LOGIN, email)
                apply()
            }
            onSuccessSignIn(UserInfo(email, "Johnny Woo"))
        }
    }

    override fun getProfile(): UserInfo {
        val sharedPref = fragment.activity?.getPreferences(Context.MODE_PRIVATE)
        val account = sharedPref?.getString(EMAIL_LOGIN, "")
        return UserInfo(email, account ?: "")
    }

    override fun signOut() {
        val sharedPref = fragment.activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            remove(EMAIL_LOGIN)
            apply()
        }
        onSuccessSignOut()
    }
}