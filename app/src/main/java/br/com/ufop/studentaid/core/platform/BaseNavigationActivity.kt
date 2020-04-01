package br.com.ufop.studentaid.core.platform

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation

abstract class BaseNavigationActivity(layoutRes: Int) :
    BaseActivity(layoutRes) {


    /**
     * - Resource *id* identifier of your main nav graph start destination, for handling onBackPressed behavior
     *  > Ex:
     *
     *   >*R.id.home_fragment_dest*
     */
    abstract fun navGraphStartDestination(): Int

    abstract fun navHostFragment(): Int

    private lateinit var navController: NavController
    internal lateinit var navDestination: NavDestination

    abstract fun toolbarTitle(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()
        setUpNavControllerAndAppbar()
//        img_home?.setOnClickListener {
//            onBackPressed()
//        }
//        img_home?.let {
//            ClickGuard.guard(it)
//        }

    }

    private fun setUpToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        title_toolbar?.text = toolbarTitle()
    }
    /**
     * - Set up Activity's Navigation Controller and it's destination changed listener
     */
    private fun setUpNavControllerAndAppbar() {
        navController = Navigation.findNavController(this, navHostFragment())
//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
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

}
