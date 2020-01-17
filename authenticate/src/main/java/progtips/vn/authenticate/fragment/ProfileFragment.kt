package progtips.vn.authenticate.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.fragment_profile.*

import progtips.vn.authenticate.R

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignOut.setOnClickListener { signOut() }
    }

    override fun onStart() {
        super.onStart()

        handleAccount()
    }

    private fun handleAccount() {
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if(account != null) {
            tvLabel.text = "Welcome ${account.displayName}"
        } else
            findNavController().navigate(R.id.loginFragment)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) {
                findNavController().navigate(R.id.loginFragment)
            }
    }
}