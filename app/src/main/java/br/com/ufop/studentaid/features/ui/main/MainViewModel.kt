package br.com.ufop.studentaid.features.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ufop.studentaid.core.platform.BaseViewModel
import br.com.ufop.studentaid.core.platform.SingleLiveEvent
import br.com.ufop.studentaid.features.models.FirestoreUser
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel

    val firebaseUser = MutableLiveData<FirebaseUser>()
    val loggedFirestoreUser = MutableLiveData<FirestoreUser>()
    val listFirestoreUsers = MutableLiveData<ArrayList<FirestoreUser>>()
    val usersResult = SingleLiveEvent<QuerySnapshot>()

    fun setLoggedUser(user: FirebaseUser?) {
        firebaseUser.value = user
    }
    fun getLoggedFirebaseUser() = firebaseUser.value

    fun setLoggedFirestoreUser(firebaseUser: FirestoreUser) {
        loggedFirestoreUser.postValue(firebaseUser)
    }
    fun setListFirestoreUsers(list: ArrayList<FirestoreUser>) {
        listFirestoreUsers.postValue(list)
    }
    fun getLoggedFirestoreUser() = loggedFirestoreUser.value

    fun fetchUsersList() {
        viewModelScope.launch {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    usersResult.postValue(result)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }

    }
}