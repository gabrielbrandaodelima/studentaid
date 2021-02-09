package br.com.ufop.studentaid.features.models

import com.google.android.gms.maps.model.LatLng

/**
 * Example of a Kotlin data class
 * >Analog to a *POJO*
 */
data class UserModel(
        val name: String? = null,
        val position: LatLng = LatLng(0.0, 0.0),
        val userId: String? = null,
        val providedServicesArray: ArrayList<ServiceModel> = arrayListOf(),
        val takenServicesArray: ArrayList<ServiceModel> = arrayListOf(),
        val rating : Double = 0.0,
        val contactPhone: String? = null,
        val ratingsList: List<UserRating> = arrayListOf()
)