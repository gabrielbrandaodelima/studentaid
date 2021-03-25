package br.com.ufop.studentaid.features.util

import br.com.ufop.studentaid.features.models.ServiceModel
import br.com.ufop.studentaid.features.models.UserModel

object MockUtils {

    fun getProvidedServices(): List<String> {
        return arrayListOf(
            "Suporte TI",
            "Encanador",
            "Bombeiro"
        )
    }

    fun getListServices() : ArrayList<String>{
        return arrayListOf(
                "Suporte TI",
                "Encanador",
                "Bombeiro",
                "Pedreiro",
                "Eletricista",
                "Professor",
                "Informática",
                "Personal Trainer",
                "Nutricionista",
                "Carreto",
                "Taxi",
                "Pet Shop",
                "Fotografia",
                "Assistência Técnica",
                "Instalação E Montagem De Aparelhos",
                "Colocação De Molduras E Congêneres",
                "Tinturaria E Lavanderia",
                "Tapeçaria ",
                "Funilaria",
                "Carpintaria",
                "Guincho",
                "Advocacia",
                "Auditoria",
                "Estatística",
                "Assessoria"
        )
    }
    fun getContractedServices(): List<ServiceModel> {
        return arrayListOf(
            ServiceModel("Bombeiro", "1"),
            ServiceModel("Encanador", "2"),
            ServiceModel("Pedreiro", "3"),
            ServiceModel("Eletricista", "4")
        )
    }

    fun getUsers(): List<UserModel> {

        return emptyList()
    }

}