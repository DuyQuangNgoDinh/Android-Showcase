package progtips.vn.authenticate.fragment.manager

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import progtips.vn.authenticate.R
import progtips.vn.authenticate.fragment.entity.UserInfo

abstract class AuthMethod(
    private val fragment: Fragment
){
    abstract fun alreadyLoggedIn(): Boolean

    abstract fun signIn()

    abstract fun getProfile(): UserInfo

    abstract fun signOut()

    open fun onSuccessSignIn(userInfo: UserInfo) {
        fragment.findNavController().popBackStack()
    }

    open fun onSuccessSignOut() {
        fragment.findNavController().navigate(R.id.loginFragment)
    }
}