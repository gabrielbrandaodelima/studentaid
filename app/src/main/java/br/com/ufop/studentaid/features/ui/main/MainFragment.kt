package br.com.ufop.studentaid.features.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment

class MainFragment : BaseFragment(R.layout.main_fragment) {

      private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}