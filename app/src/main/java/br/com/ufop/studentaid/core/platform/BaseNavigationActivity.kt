package br.com.ufop.studentaid.core.platform

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.features.ui.login.LoginActivity
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.navigation_view.*

abstract class BaseNavigationActivity(layoutRes: Int) :
    AppCompatActivity(layoutRes) {
    var baseGoogleMap: GoogleMap? = null
    val REQUEST_LOCATION_PERMISSION = 1
    /**
     * - Resource *id* identifier of your main nav graph start destination, for handling onBackPressed behavior
     *  > Ex:
     *
     *   >*R.id.home_fragment_dest*
     */
    abstract fun navGraphStartDestination(): Int

    abstract fun navHostFragment(): Int

    private val navController by lazy {
        findNavController(navHostFragment())
    }
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

        logout_text_view?.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
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
    fun setUpNavControllerAndAppbar() {
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

    fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    var  permGranted:() -> Unit = {}
    fun enableMyLocation(permGranted: () -> Unit) {
        if (isPermissionGranted()) {
//            map.isMyLocationEnabled = true
            permGranted()
        } else {
            this.permGranted = permGranted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            if (requestCode == REQUEST_LOCATION_PERMISSION) {
                if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        enableMyLocation(permGranted)
                        return
                    }
                    enableMyLocation(permGranted)

                } else {
                    enableMyLocation(permGranted)
                }
            }
    }

}
