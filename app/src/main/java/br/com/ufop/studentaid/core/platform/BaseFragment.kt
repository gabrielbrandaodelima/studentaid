package br.com.ufop.studentaid.core.platform

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.app_bar_layout.*

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    abstract fun toolbarTitle(): String


    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    val storage = Firebase.storage
    var mActivity: BaseNavigationActivity? = null
    val currentUserUid = Firebase.auth.currentUser?.uid
    val userLoginReference by lazy {
        db.document("users/${currentUserUid}")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            mActivity = if (it is BaseNavigationActivity)
                it
            else
                null
        }
    }

    fun setToolbarTitle() {
        mActivity?.apply {
            toolbar_main?.let {
                it.postDelayed({
                    TransitionManager.beginDelayedTransition(it, AutoTransition())
                    app_bar_title?.text = this@BaseFragment.toolbarTitle()
                }, 600)
            }
        }
    }
    fun showMessage(message: String, isErrorMessage: Boolean = false) {

        if (isErrorMessage) {
            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
        } else {

            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
        }
    }
    fun createAlertDialog(title: String, message: String, positiveCallback:()->Unit, negativeCallback: ()-> Unit) {

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(title) // O Titulo da notificação
        alertDialog.setMessage(message) // a mensagem ou alerta
        alertDialog.setPositiveButton("Ok") { _, _ ->
            positiveCallback()
        }

        alertDialog.setNegativeButton("Cancelar") { _, _ ->
            negativeCallback()
        }
        alertDialog.show()
    }

}