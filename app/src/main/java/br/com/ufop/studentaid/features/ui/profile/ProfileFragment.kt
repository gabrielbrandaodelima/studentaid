package br.com.ufop.studentaid.features.ui.profile

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment

class ProfileFragment : BaseFragment(R.layout.profile_fragment) {

    override fun toolbarTitle(): String = this::class.java.simpleName

    private lateinit var viewModel: ProfileViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}