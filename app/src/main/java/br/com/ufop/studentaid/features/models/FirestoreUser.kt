package br.com.ufop.studentaid.features.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

/**
 * Example of a Kotlin data class
 * >Analog to a *POJO*
 */
@Parcelize
data class FirestoreUser(
    val uid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val rating: Int = 1
):Parcelable