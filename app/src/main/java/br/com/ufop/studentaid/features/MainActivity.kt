package br.com.ufop.studentaid.features

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.gone
import br.com.ufop.studentaid.core.extensions.visible
import br.com.ufop.studentaid.core.platform.BaseNavigationActivity
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.navigation_view.view.*

class MainActivity : BaseNavigationActivity(R.layout.main_activity) {

    private lateinit var viewModel: MainViewModel

    val navHeaderView by lazy {
        drawer_layout.nav_view.getHeaderView(0)
    }

    override fun navGraphStartDestination(): Int = R.id.mainFragmentDest

    override fun navHostFragment(): Int = R.id.main_nav_host_fragment

    override fun toolbarTitle(): String = getString(R.string.app_name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModels()

        setUpNavHeader()
        app_bar_layout?.gone()
    }

    private fun setUpNavHeader() {
        navHeaderView.apply {
            stars_layout?.rating = 1F
            profile_name?.text = "Gabriel Lima"
            profile_mail?.text = "gabrielblimapas@gmail.com"
        }
    }

    private fun setUpViewModels() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onDestinationChangedListener(controller: NavController, destination: NavDestination, bundle: Bundle?) {
        super.onDestinationChangedListener(controller, destination, bundle)
        if (destination.id == R.id.loginFragment) {
            app_bar_layout?.gone()
        } else {
            app_bar_layout?.visible()
        }
    }

    override fun onBackPressed() {
        if (navDestination.id == R.id.loginFragment) {
            finish()
        } else {
            super.onBackPressed()
        }

    }
}