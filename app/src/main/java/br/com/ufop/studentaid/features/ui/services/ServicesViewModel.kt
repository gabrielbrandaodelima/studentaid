package br.com.ufop.studentaid.features.ui.services

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.ufop.studentaid.core.platform.BaseViewModel
import br.com.ufop.studentaid.core.platform.SingleLiveEvent
import br.com.ufop.studentaid.features.models.FirestoreUser
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch

class ServicesViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel

    val serviceSelected = SingleLiveEvent<String>()

    val servicesResult = MutableLiveData<QuerySnapshot>()

    val localProvidedServicesList = ArrayList<String>()
    val providedServicesList = MutableLiveData<ArrayList<String>>()
    fun setServiceSelected(serviceModel: String) {
        serviceSelected.postValue(serviceModel)
    }
    fun getServiceSelected() = serviceSelected.value

    fun fetchProvidedServicesList(arrayList: ArrayList<FirestoreUser>) {
        viewModelScope.launch {
            localProvidedServicesList.clear()
            arrayList.forEach {
                it.providedServices?.let {
                    addProvidedServices(it)
                }
            }
            providedServicesList.postValue(localProvidedServicesList)
        }

    }

    private fun addProvidedServices(it: java.util.ArrayList<String>) {
        localProvidedServicesList.addAll(it)
    }
}