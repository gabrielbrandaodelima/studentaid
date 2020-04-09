package br.com.ufop.studentaid.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.ufop.studentaid.R
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.navigation_view.*

abstract class BaseNavigationActivity(layoutRes: Int) :
    AppCompatActivity(layoutRes) {


    /**
     * - Resource *id* identifier of your main nav graph start destination, for handling onBackPressed behavior
     *  > Ex:
     *
     *   >*R.id.home_fragment_dest*
     */
    abstract fun navGraphStartDestination(): Int

    abstract fun navHostFragment(): Int

    private val navController by lazy { findNavController(R.id.main_nav_host_fragment) }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            navController.graph,
            findViewById(R.id.drawer_layout)
        )
    }
    internal lateinit var navDestination: NavDestination

    abstract fun toolbarTitle(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()
        setUpNavControllerAndAppbar()
        logout_text_view?.setOnClickListener {
            finish()
        }

    }

    private fun setUpToolbar() {
        toolbar_main?.let {

            setSupportActionBar(it)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        app_bar_title?.text = toolbarTitle()
    }

    /**
     * - Set up Activity's Navigation Controller and it's destination changed listener
     */
    private fun setUpNavControllerAndAppbar() {
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view?.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, bundle ->
            onDestinationChangedListener(controller, destination, bundle)
        }

    }


    /**
     * - Override on activity which extends [BaseActivity],
     * to handle nav controller destination changed
     */
    open fun onDestinationChangedListener(
        controller: NavController,
        destination: NavDestination,
        bundle: Bundle?
    ) {
        navDestination = destination
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
}
