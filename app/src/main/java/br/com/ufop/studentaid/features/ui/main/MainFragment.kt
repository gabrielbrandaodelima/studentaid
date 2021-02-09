package br.com.ufop.studentaid.features.ui.main

import android.annotation.SuppressLint
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.ui.login.LoginViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*

class MainFragment : BaseFragment(R.layout.main_fragment), OnMapReadyCallback {
    private val TAG = this::class.java.simpleName
    private lateinit var viewModel: MainViewModel
    private lateinit var loginViewModel: LoginViewModel

    private var googleMap: GoogleMap? = null

    override fun toolbarTitle(): String = getString(R.string.app_name)

    //    var navController = findNavController()
    private var fusedLocationClient: FusedLocationProviderClient? = null
    var savedInstanceState: Bundle? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        setToolbarTitle()
        setUpViewModels()
        main_map?.onCreate(savedInstanceState)
        mActivity?.enableMyLocation {
            loadMap()
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
            observeViewModel()
        }

    }

    private fun observeViewModel() {
        loginViewModel.firebaseUser?.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                for (profile in it.providerData) {
                    // Id of the provider (ex: google.com)
                    val providerId = profile.providerId

                    // UID specific to the provider
                    val uid = profile.uid

                    // Name, email address, and profile photo Url
                    val name = profile.displayName
                    val email = profile.email
                    val photoUrl = profile.photoUrl
                }
                // Name, email address, and profile photo Url
                val name = user.displayName
                    val email = user.email
                val photoUrl = user.photoUrl

                // Check if user's email is verified
                val emailVerified = user.isEmailVerified

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getToken() instead.
                val uid = user.uid
            }
        })

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
        mActivity?.apply {
            baseGoogleMap = googleMap
        }
        this.googleMap = googleMap
        googleMap?.apply {
            isMyLocationEnabled = true
            setMapLongClick(this)
            setPoiClick(this)
            setMapStyle(this)
        }

        val markersOptionsList = arrayListOf<MarkerOptions>()
        MockLatLng.userMockList.forEach { userModel ->
            val markerOptions = MarkerOptions()
                .position(userModel.position)
                .snippet("${MockLatLng.latitude},${MockLatLng.longitude}")
                .title(userModel.name)

            markersOptionsList.add(markerOptions)

            googleMap?.addMarker(markerOptions)
        }
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
                        /**
                         * Zoom to current location
                         */
                        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
                    }
                },1000)

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