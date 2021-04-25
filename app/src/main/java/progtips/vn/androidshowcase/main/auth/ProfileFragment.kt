package progtips.vn.androidshowcase.main.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import progtips.vn.androidshowcase.main.auth.ui.ProfileUI
import progtips.vn.androidshowcase.main.theme.AndroidShowcaseTheme
import progtips.vn.sharedresource.helper.getAppName
import progtips.vn.androidshowcase.main.welcome.WelcomeViewModel

@AndroidEntryPoint
class ProfileFragment: Fragment() {
    private val authViewModel: AuthViewModel by viewModels()
    private val welcomeViewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AndroidShowcaseTheme {
                    PassbookProfileUI()
                }
            }
        }
    }

    @Composable
    fun PassbookProfileUI() {
        val username: String? by authViewModel.username.observeAsState()
        val isLoggedIn: Boolean by authViewModel.isLoggedIn.observeAsState(false)
        ProfileUI(username, context?.getAppName(), isLoggedIn, {
            this@ProfileFragment.findNavController().navigate(ProfileFragmentDirections.profileToLogin())
        }, {
            authViewModel.logout()
        })
    }
}
