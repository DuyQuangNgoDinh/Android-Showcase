package progtips.vn.authenticate.fragment.manager

import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import progtips.vn.authenticate.fragment.fragment.LoginFragment
import progtips.vn.authenticate.fragment.entity.UserInfo

class GoogleAuth(
    private val fragment: Fragment
): AuthMethod(fragment) {
    private var mGoogleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(fragment.requireContext(), gso)
    }

    override fun alreadyLoggedIn(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(fragment.requireContext())
        return account != null
    }

    override fun getProfile(): UserInfo {
        val account = GoogleSignIn.getLastSignedInAccount(fragment.requireContext())
        return mapUserInfo(account!!)
    }

    override fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        fragment.startActivityForResult(signInIntent, LoginFragment.GG_SIGN_IN)
    }

    override fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(fragment.requireActivity()) {
                onSuccessSignOut()
            }
    }

    private fun mapUserInfo(
        account: GoogleSignInAccount
    ) = UserInfo(account.email ?: "", account.displayName ?: "")

    fun getSignedInAccountFromIntent(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)
            onSuccessSignIn(mapUserInfo(account!!))
        } catch (e: ApiException) {
            Log.e("LoginFragment", "signInResult:failed code=" + e.statusCode)
        }
    }
}