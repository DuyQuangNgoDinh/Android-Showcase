package progtips.vn.authenticate.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*

import progtips.vn.authenticate.R
import progtips.vn.authenticate.fragment.entity.UserInfo
import progtips.vn.authenticate.fragment.manager.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authManager = AuthManager(this)
        btnSignOut.setOnClickListener { signOut() }
    }

    override fun onStart() {
        super.onStart()
        checkUserAlreadyLoggedIn()
    }

    private val showUser: (UserInfo) -> Unit = {
        tvLabel.text = "Welcome ${it.username}"
    }

    private val showLoginPage: () -> Unit = {
        findNavController().navigate(R.id.loginFragment)
    }

    private fun checkUserAlreadyLoggedIn() {
        authManager.checkUserAlreadyLoggedIn(showUser, showLoginPage)
    }

    private fun signOut() {
        authManager.currentLoginMethod!!.signOut()
    }
}