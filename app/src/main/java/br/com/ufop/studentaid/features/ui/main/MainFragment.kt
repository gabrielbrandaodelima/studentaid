package br.com.ufop.studentaid.features.ui.main

import android.annotation.SuppressLint
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.models.FirestoreUser
import br.com.ufop.studentaid.features.ui.login.LoginViewModel
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_LATITUDE
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_LONGITUDE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*

class MainFragment : BaseFragment(R.layout.main_fragment), OnMapReadyCallback {
    private var isMapReady: Boolean = false
    private var hasLoadedUsers: Boolean = false
    private var isUsersLoadedOnMap: Boolean = false
    private val TAG = this::class.java.simpleName
    private lateinit var viewModel: MainViewModel
    private lateinit var loginViewModel: LoginViewModel

    private var googleMap: GoogleMap? = null

    override fun toolbarTitle(): String = getString(R.string.app_name)

    //    var navController = findNavController()
    private var fusedLocationClient: FusedLocationProviderClient? = null
    var savedInstanceState: Bundle? = null


    val listFirestoreUsers = arrayListOf<FirestoreUser>()

    val markersOptionsList = arrayListOf<MarkerOptions>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpViewModels()
        viewModel.fetchUsersList()
        this.savedInstanceState = savedInstanceState
        setToolbarTitle()
        main_map?.onCreate(savedInstanceState)
        mActivity?.enableMyLocation {
            loadMap()
        }
    }

    private fun createFirebaseUsersList(result: QuerySnapshot) {
        listFirestoreUsers.clear()
        for (document in result) {
            val firebaseUser = document.toObject(FirestoreUser::class.java)
            if (firebaseUser.uid != viewModel.getLoggedFirebaseUser()?.uid)
                listFirestoreUsers.add(firebaseUser)
            else
                viewModel.setLoggedFirestoreUser(firebaseUser)
            Log.d(TAG, "${document.id} => ${document.data}")
        }
        viewModel.setListFirestoreUsers(listFirestoreUsers)
        hasLoadedUsers = true
    }

    private fun inflateUsersLocationOnMap(arrayList: ArrayList<FirestoreUser>?) {
        if (isMapReady && hasLoadedUsers) {
            arrayList?.forEach { userModel ->
                val markerOptions = MarkerOptions()
                        .position(LatLng(userModel.latitude, userModel.longitude))
                        .snippet("${userModel.latitude},${userModel.longitude}")
                        .title(userModel.name)

                if (markersOptionsList.contains(markerOptions).not()){
                    markersOptionsList.add(markerOptions)
                    googleMap?.addMarker(markerOptions)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        main_map?.onResume()

    }

    override fun onPause() {
        super.onPause()
        main_map?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        main_map?.onDestroy()
    }

    private fun loadMap() {
        main_map?.onResume()
        main_map?.getMapAsync(this)
        context?.let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
        }

    }

    private fun setUpViewModels() {
        activity?.let {
            loginViewModel = ViewModelProvider(it).get(LoginViewModel::class.java)
            viewModel = ViewModelProvider(it).get(MainViewModel::class.java)
            observe(viewModel)
        }

    }

    private fun observe(viewModel: MainViewModel) {
        viewModel.usersResult.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            createFirebaseUsersList(it)
        })
        viewModel.listFirestoreUsers.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            populateMap(it)
        })
    }

    private fun populateMap(it: ArrayList<FirestoreUser>?) {
        inflateUsersLocationOnMap(it)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        isMapReady = true
        inflateUsersLocationOnMap(viewModel.listFirestoreUsers.value)
        mActivity?.apply {
            baseGoogleMap = googleMap
        }
        googleMap?.apply {
            isMyLocationEnabled = true
            setMapLongClick(this)
            setPoiClick(this)
            setMapStyle(this)
            setOnInfoWindowClickListener {
                val position = it.position
                val name = it.title
                val lat = position.latitude
                val long = position.longitude
                listFirestoreUsers.forEach {
                    if (it.name == name && it.latitude == lat && it.longitude == long) {
                        findNavController().navigate(R.id.profileFragment, Bundle().apply {
                            putParcelable(getString(R.string.PROFILE_CLICKED), it)
                        })
                        return@setOnInfoWindowClickListener
                    }
                }
            }
        }


//        inflateUsersLocationOnMap()

        setMyLocation()
        // Add a marker in Sydney and move the camera
//        val pasargada = LatLng(-20.399039, -43.513923)
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap?.addMarker(MarkerOptions().position(pasargada).title("Republica Pasárgada"))
//        this.googleMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//
//        val latitude = -20.399077
//        val longitude = -43.514099
//
//        val homeLatLng = LatLng(latitude, longitude)
//
//        val zoomLevel = 15f
//
//        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
//        this.googleMap?.addMarker(MarkerOptions().position(homeLatLng).title("Republica Pasárgada"))
    }


    @SuppressLint("MissingPermission")
    private fun setMyLocation() {
        fusedLocationClient?.lastLocation
                ?.addOnSuccessListener { location: Location? ->
                    view?.postDelayed({
                        location?.let {


                            userLoginReference.update(
                                    KEY_LATITUDE, it.latitude,
                                    KEY_LONGITUDE, it.longitude
                            )
                            val markerOptions = MarkerOptions()
                                    .position(LatLng(it.latitude, it.longitude))
                                    .snippet("${it.latitude},${it.longitude}")
                                    .title("Você")

                            googleMap?.addMarker(markerOptions)
                            /**
                             * Zoom to current location
                             */
                            googleMap?.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                            LatLng(
                                                    it.latitude,
                                                    it.longitude
                                            ), 15f
                                    )
                            )
                        }
                    }, 1000)

                }
    }

    var snippet = ""
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            // A Snippet is Additional text that's displayed below the title.
            snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    latLng.latitude,
                    latLng.longitude
            )
            map.addMarker(
                    MarkerOptions()
                            .position(latLng)
                            .title("Dropped pin")
                            .snippet(snippet).icon(
                                    BitmapDescriptorFactory.defaultMarker()
                            )

            )
        }
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    poi.latLng.latitude,
                    poi.latLng.longitude
            )
            val poiMarker = map.addMarker(
                    MarkerOptions().snippet(snippet).icon(
                            BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_GREEN
                            )
                    )
                            .position(poi.latLng)
                            .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            requireContext(),
                            R.raw.map_style_standard
                    )
            )

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

}