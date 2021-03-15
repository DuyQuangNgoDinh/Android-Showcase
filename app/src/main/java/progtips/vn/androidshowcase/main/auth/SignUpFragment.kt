package progtips.vn.androidshowcase.main.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import progtips.vn.androidshowcase.BaseFragment
import progtips.vn.androidshowcase.R
import progtips.vn.androidshowcase.databinding.FmSignupBinding
import progtips.vn.androidshowcase.main.auth.model.AuthState

@AndroidEntryPoint
class SignUpFragment: BaseFragment<FmSignupBinding>(R.layout.fm_signup) {
    private val authViewModel: AuthViewModel by viewModels()

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
