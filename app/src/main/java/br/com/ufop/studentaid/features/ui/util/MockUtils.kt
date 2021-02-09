package br.com.ufop.studentaid.features.ui.util

import br.com.ufop.studentaid.features.models.ProfessionalServiceModel
import br.com.ufop.studentaid.features.models.ServiceModel
import br.com.ufop.studentaid.features.models.UserModel
import io.bloco.faker.Faker

object MockUtils {

    fun getProvidedServices(): List<ServiceModel> {
        return arrayListOf(
            ServiceModel("Suporte TI", "1"),
            ServiceModel("Encanador", "2"),
            ServiceModel("Bombeiro","3")
        )
    }

    fun getContractedServices(): List<ServiceModel> {
        return arrayListOf(
            ServiceModel("Bombeiro", "1"),
            ServiceModel("Encanador", "2"),
            ServiceModel("Pedreiro", "3"),
            ServiceModel("Eletricista", "4"),
            ServiceModel("Suporte TI", "4")
        )
    }

    fun getUsers(): List<UserModel> {

        return emptyList()
    }

}