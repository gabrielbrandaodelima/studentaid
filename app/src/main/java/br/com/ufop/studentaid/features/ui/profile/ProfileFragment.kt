package br.com.ufop.studentaid.features.ui.profile

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.gone
import br.com.ufop.studentaid.core.extensions.visibility
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.ui.login.LoginViewModel
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : BaseFragment(R.layout.profile_fragment) {

    override fun toolbarTitle(): String = getString(R.string.text_profile)

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setUpViewModels()
        observeUser()
    }

    private fun setUpViewModels() {
        activity?.let {
            viewModel = ViewModelProvider(it).get(MainViewModel::class.java)
        }

    }

    private fun observeUser() {
        viewModel.firebaseUser?.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                // Name, email address, and profile photo Url
                val name = user.displayName
                val email = user.email
                val photoUrl = user.photoUrl
                val phoneNumber = user.phoneNumber

                // Check if user's email is verified
                val emailVerified = user.isEmailVerified

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getToken() instead.
                val uid = user.uid
                profilefragment_textview_name?.text = name
                profile_email_text?.text = email
                profile_phone_text?.text = phoneNumber
                profile_phone_cell  ?.visibility(phoneNumber.isNullOrBlank().not())
                Picasso.get().load(photoUrl).into(profilefragment_image)
                profile_star?.rating = 1f
            }
        })
    }


}