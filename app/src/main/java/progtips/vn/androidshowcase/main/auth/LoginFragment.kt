package progtips.vn.androidshowcase.main.auth

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import progtips.vn.androidshowcase.BaseFragment
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.databinding.FmLoginBinding
import progtips.vn.androidshowcase.main.auth.model.AuthState
import progtips.vn.sharedresource.vmevent.EventObserver

@AndroidEntryPoint
class LoginFragment: BaseFragment<FmLoginBinding>(R.layout.fm_login) {
    private val loginViewModel: LoginViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FmLoginBinding {
        return FmLoginBinding.inflate(inflater, container, false)
    }

    override fun initView(view: View) {
        binding.run {
            tvSignup.setOnClickListener {
                it.findNavController().navigate(LoginFragmentDirections.loginToSignUp())
            }

            socialLogin.btnGoogleLogin.setOnClickListener {
                loginViewModel.loginWithGoogle(this@LoginFragment)
            }

            socialLogin.btnFacebookLogin.setOnClickListener {
                loginViewModel.loginWithFacebook(this@LoginFragment)
            }

            btnSignIn.setOnClickListener {
                loginViewModel.loginWithEmailPassword(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
    }

    override fun registerObserver() {
        authViewModel.authStateLiveData.observe(viewLifecycleOwner) {
            if (it is AuthState.Authenticated) {
                findNavController().popBackStack(R.id.profileFragment, false)
            }
        }

        loginViewModel.isInProgress().observe(viewLifecycleOwner) {
            displayLoading(it)
        }

        loginViewModel.getError().observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loginViewModel.onActivityResult(requestCode, resultCode, data)
    }
}
