package br.com.ufop.studentaid.features.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : BaseFragment(R.layout.main_fragment), OnMapReadyCallback {

    private lateinit var viewModel: MainViewModel

    private var googleMap: GoogleMap? = null

    override fun toolbarTitle(): String = getString(R.string.app_name)

    val map : MapView by lazy {
        main_map
    }
    //    var navController = findNavController()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.onCreate(savedInstanceState)
        setUpViewModels()


    }

    override fun onResume() {
        super.onResume()
        map.onResume()
        loadMap()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    private fun loadMap() {
        map.getMapAsync(this)

    }

    private fun setUpViewModels() {
        activity?.let {
            viewModel = ViewModelProvider(it).get(MainViewModel::class.java)
        }

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
    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
//        googleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        // Add a marker in Sydney and move the camera
        val pasargada = LatLng(-20.399039, -43.513923)
        val sydney = LatLng(-34.0, 151.0)
        googleMap?.addMarker(MarkerOptions().position(pasargada).title("Republica Pasárgada"))
//        this.googleMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLng(pasargada))
//        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(pasargada,1.0F))
    }

}