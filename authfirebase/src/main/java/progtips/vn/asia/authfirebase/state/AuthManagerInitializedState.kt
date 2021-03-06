package progtips.vn.asia.authfirebase.state

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import progtips.vn.asia.authfirebase.R
import progtips.vn.asia.authfirebase.account.toAccount
import progtips.vn.asia.authfirebase.auth.AuthListener
import progtips.vn.asia.authfirebase.auth.AuthStatus

class AuthManagerInitializedState(
    private val activity: Activity,
    private var authListener: AuthListener? = null
): AuthManagerState {

    companion object {
        private const val RC_LOGIN_GOOGLE = 1001

        fun getCurrentUser() = FirebaseAuth.getInstance().currentUser?.toAccount()
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    private val resources = activity.resources

    init {
        initGoogleSignIn()
        initFacebookSignIn()
    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(resources.getString(R.string.google_server_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    private fun initFacebookSignIn() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                authListener?.onCancel(AuthStatus.CancelFacebookLogin)
            }

            override fun onError(error: FacebookException) {
                authListener?.onError(AuthStatus.ErrorFacebookLogin, error.message)
            }
        })
    }

    override fun setAuthListener(listener: AuthListener) {
        authListener = listener
    }

    override fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    authListener?.onSuccess(AuthStatus.SuccessEmailLogin, getCurrentUser())
                } else {
                    authListener?.onError(AuthStatus.ErrorEmailLogin, task.exception?.message)
                }
            }
    }

    override fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    authListener?.onSuccess(AuthStatus.SuccessSignUp, getCurrentUser())
                } else {
                    authListener?.onError(AuthStatus.ErrorSignUp, resources.getString(R.string.error_signup))
                }
            }
    }

    override fun logout() {
        auth.signOut()
        authListener?.onSuccess(AuthStatus.SuccessLogout, null)
    }

    override fun loginWithGoogle(fragment: Fragment) {
        fragment.startActivityForResult(googleSignInClient.signInIntent, RC_LOGIN_GOOGLE)
    }

    override fun loginWithFacebook(fragment: Fragment) {
        LoginManager.getInstance().logInWithReadPermissions(fragment, listOf("email", "public_profile"))
    }

    /**
     * Login to Firebase Auth with Google Account
     */
    private fun handleGoogleAccount(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    googleSignInClient.signOut() // Already logged in via firebase, do not need it anymore
                    authListener?.onSuccess(AuthStatus.SuccessGoogleLogin, getCurrentUser())
                } else {
                    authListener?.onError(AuthStatus.ErrorGoogleLogIn, resources.getString(R.string.error_signin_google))
                }
            }
    }

    /**
     * Login to Firebase Auth with Facebook Account
     */
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    LoginManager.getInstance().logOut() // Already logged in via firebase, do not need it anymore
                    authListener?.onSuccess(AuthStatus.SuccessFacebookLogin, getCurrentUser())
                } else {
                    authListener?.onError(AuthStatus.ErrorFacebookLogin, resources.getString(R.string.error_signin_facebook))
                }
            }
    }

    override fun onFacebookLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onGoogleLoginActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_LOGIN_GOOGLE) {
            if (resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    handleGoogleAccount(account!!)
                } catch (e: ApiException) {
                    authListener?.onError(AuthStatus.ErrorGoogleLogIn, e.message)
                }
            } else {
                authListener?.onCancel(AuthStatus.CancelGoogleLogin)
            }
        }
    }
}