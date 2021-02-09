package br.com.ufop.studentaid.core.platform

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import br.com.ufop.studentaid.features.MainActivity
import kotlinx.android.synthetic.main.app_bar_layout.*

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    abstract fun toolbarTitle(): String


    var mActivity: BaseNavigationActivity? = null

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
}