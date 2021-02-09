package br.com.ufop.studentaid.features.ui.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.ui.login.LoginViewModel
import br.com.ufop.studentaid.features.ui.main.MainViewModel

class HomeFragment : BaseFragment(R.layout.home_fragment) {

    override fun toolbarTitle(): String = getString(R.string.title_home)

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        activity?.let { viewModel = ViewModelProvider(it).get(MainViewModel::class.java) }
        // TODO: Use the ViewModel
        observeUser()
    }

    private fun observeUser() {
        viewModel.firebaseUser?.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                for (profile in it.providerData) {
                    // Id of the provider (ex: google.com)
                    val providerId = profile.providerId

                    // UID specific to the provider
                    val uid = profile.uid

                    // Name, email address, and profile photo Url
                    val name = profile.displayName
                    val email = profile.email
                    val photoUrl = profile.photoUrl
                }
                    // Name, email address, and profile photo Url
                    val name = user.displayName
                    val email = user.email
                    val photoUrl = user.photoUrl

                    // Check if user's email is verified
                    val emailVerified = user.isEmailVerified

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    val uid = user.uid
            }
        })
    }

}