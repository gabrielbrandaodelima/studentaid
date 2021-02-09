package br.com.ufop.studentaid.features.ui.main

import br.com.ufop.studentaid.features.models.UserModel
import com.google.android.gms.maps.model.LatLng

class MockLatLng {
    companion object {

        val latitude = -20.399077
        val longitude = -43.514099
        val homeLatLng = LatLng(latitude, longitude)
        val p3 = LatLng(-20.39816341009528, -43.51294543594122)
        val p4 = LatLng(-20.401238335437935, -43.51499196141958)
        val p5 = LatLng(-20.402496256036383, -43.50856974720955)

        val pasargada = UserModel("Pasargada", LatLng(-20.399039, -43.513923))
        val pasargada2 = UserModel("Point 2", homeLatLng)
        val pasargada3 = UserModel("Point 3", p3)
        val pasargada4 = UserModel("Point 4", p4)
        val pasargada5 = UserModel("Point 5", p5)

        val userMockList = arrayListOf(
            pasargada,
            pasargada2,
            pasargada3,
            pasargada4,
            pasargada5
        )
        val sydney = LatLng(-34.0, 151.0)
    }
}