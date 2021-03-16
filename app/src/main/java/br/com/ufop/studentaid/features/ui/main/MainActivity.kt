package br.com.ufop.studentaid.features.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.gone
import br.com.ufop.studentaid.core.extensions.visible
import br.com.ufop.studentaid.core.platform.BaseNavigationActivity
import br.com.ufop.studentaid.features.models.FirestoreUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.app_bar_searchable.*
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

    private var auth: FirebaseAuth? = null
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpNavControllerAndAppbar()
        auth = Firebase.auth
        setUpViewModels()
        getCurrentUser()

        setUpNavHeader()
    }

    private fun getCurrentUser() {
        val user = auth?.currentUser
        viewModel.setLoggedUser(user)
    }

    private fun setUpNavHeader(firestoreUser: FirestoreUser? = null) {

        if (firestoreUser != null) {
            navHeaderView.apply {
                stars_layout?.rating = firestoreUser.rating.toFloat()
                profile_name?.text = firestoreUser.name
                profile_mail?.text = firestoreUser.email
                Picasso.get().load(firestoreUser.photoUrl).into(profile_image)
            }
        } else {
            val user = viewModel.getLoggedFirebaseUser()
            user?.apply {
                navHeaderView.apply {
                    stars_layout?.rating = 1F
                    profile_name?.text = displayName
                    profile_mail?.text = email
                    Picasso.get().load(photoUrl).into(profile_image)
                }
            }
        }
    }

    private fun setUpViewModels() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        observe()
    }

    private fun observe() {
        viewModel.loggedFirestoreUser?.observe(this, Observer {
            setUpNavHeader(it)
        })
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onDestinationChangedListener(controller: NavController, destination: NavDestination, bundle: Bundle?) {
        super.onDestinationChangedListener(controller, destination, bundle)
        if (destination.id == R.id.mainFragmentDest) {
            app_bar_searchable?.visible()
        } else {
            app_bar_searchable?.gone()
        }
    }

//    override fun onBackPressed() {
//        if (navDestination.id == R.id.loginFragment) {
//            finish()
//        } else {
//            super.onBackPressed()
//        }
//
//    }
}