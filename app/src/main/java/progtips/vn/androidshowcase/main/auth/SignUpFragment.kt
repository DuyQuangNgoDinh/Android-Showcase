package progtips.vn.androidshowcase.main.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import progtips.vn.asia.authfirebase.account.Account
import progtips.vn.asia.authfirebase.auth.AuthListener
import progtips.vn.asia.authfirebase.auth.AuthStatus
import progtips.vn.androidshowcase.BaseFragment
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.databinding.FmSignupBinding
import progtips.vn.androidshowcase.main.auth.model.AuthState

@AndroidEntryPoint
class SignUpFragment: BaseFragment<FmSignupBinding>(R.layout.fm_signup) {
    private val loginViewModel: LoginViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private val authListener = object: AuthListener {
        override fun onSuccess(status: AuthStatus, account: Account?) {

        }

        override fun onCancel(status: AuthStatus) {
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
        }

        override fun onError(status: AuthStatus, message: String?) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FmSignupBinding {
        return FmSignupBinding.inflate(inflater, container, false)
    }

    override fun initView(view: View) {

    }

    override fun registerObserver() {
        authViewModel.authStateLiveData.observe(viewLifecycleOwner) {
            if (it is AuthState.Authenticated) {
                findNavController().popBackStack(R.id.profileFragment, false)
            }
        }
    }
}
