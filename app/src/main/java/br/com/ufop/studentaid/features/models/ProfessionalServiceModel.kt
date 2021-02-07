package br.com.ufop.studentaid.features.models

import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * Example of a Kotlin data class
 * >Analog to a *POJO*
 */
data class ProfessionalServiceModel (
    val serviceName: String? = null,
    val serviceId: String? = null,
    val serviceModel: ServiceModel? = null,
    val userMapModel: UserMapModel? = null,
    val date: Date? = null
)