package br.com.ufop.studentaid.features.models

import com.google.android.gms.maps.model.LatLng

/**
 * Example of a Kotlin data class
 * >Analog to a *POJO*
 */
data class UserMapModel(
        val userId: String? = null,
        val name: String? = null,
        val position: LatLng = LatLng(0.0, 0.0),
        val providedServicesArray: ArrayList<ServiceModel> = arrayListOf(),
        val takenServicesArray: ArrayList<ServiceModel> = arrayListOf()
)