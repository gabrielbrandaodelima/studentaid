package br.com.ufop.studentaid.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    private val _errorGen = SingleLiveEvent<Boolean>()
    private val _errorCon = SingleLiveEvent<Boolean>()

    val db = Firebase.firestore
    val storage = Firebase.storage
    val errorGen: LiveData<Boolean> = _errorGen
    val errorCon: LiveData<Boolean> = _errorCon

    fun handleGenericFailure() {
        _errorGen.call()
    }

    fun handleConnectionFailure() {
        _errorCon.call()
    }

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    val viewModelJob = Job()


    val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}