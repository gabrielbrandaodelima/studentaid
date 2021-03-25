package br.com.ufop.studentaid.features.ui.services

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ufop.studentaid.core.platform.BaseViewModel
import br.com.ufop.studentaid.core.platform.SingleLiveEvent
import br.com.ufop.studentaid.features.models.FirestoreUser
import br.com.ufop.studentaid.features.models.ServiceModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch

class ServicesViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel

    val serviceSelected = SingleLiveEvent<String>()

    val servicesResult = MutableLiveData<QuerySnapshot>()
    fun setServiceSelected(serviceModel: String) {
        serviceSelected.postValue(serviceModel)
    }
    fun getServiceSelected() = serviceSelected.value

    fun fetchProvidedServicesList() {
        viewModelScope.launch {
            db.collection("services")
                .get()
                .addOnSuccessListener { result ->
                    servicesResult.postValue(result)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting services.", exception)
                }
        }

    }
}