package br.com.ufop.studentaid.features.models

import java.util.*

/**
 * Example of a Kotlin data class
 * >Analog to a *POJO*
 */
data class ProfessionalServiceModel (
    val serviceName: String? = null,
    val serviceId: String? = null,
    val serviceModel: ServiceModel? = null,
    val userModel: UserModel? = null,
    val date: Date? = null,
    val price: String? = null
)