package br.com.ufop.studentaid.features.ui.login

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

class LoginActivity : BaseNavigationActivity(R.layout.activity_login) {

    private lateinit var viewModel: MainViewModel
    override fun navGraphStartDestination(): Int = R.id.loginFragment

    override fun navHostFragment(): Int = R.id.login_nav_host_fragment

    override fun toolbarTitle(): String = getString(R.string.app_name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}