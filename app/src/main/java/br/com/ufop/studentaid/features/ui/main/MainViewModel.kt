package br.com.ufop.studentaid.features.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ufop.studentaid.core.platform.BaseViewModel
import com.google.firebase.auth.FirebaseUser

class MainViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel

    val firebaseUser = MutableLiveData<FirebaseUser>()

    fun setLoggedUser(user: FirebaseUser?) {
        firebaseUser.value = user
    }
    fun getLoggedUser() = firebaseUser.value
}