package br.com.ufop.studentaid.features.ui.models

import com.google.android.gms.maps.model.LatLng

data class UserMapModel (
    val name: String? = null,
    val position: LatLng? = null
)