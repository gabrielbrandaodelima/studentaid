package br.com.ufop.studentaid.features.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ufop.studentaid.core.platform.BaseViewModel
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : BaseViewModel() {
    val firebaseUser = MutableLiveData<FirebaseUser>()

    fun setLoggedUser(user: FirebaseUser) {
        firebaseUser.value = user
    }
    fun getLoggedUser() = firebaseUser.value



}