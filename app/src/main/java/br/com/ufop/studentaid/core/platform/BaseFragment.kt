package br.com.ufop.studentaid.core.platform

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import br.com.ufop.studentaid.features.MainActivity
import kotlinx.android.synthetic.main.app_bar_layout.*

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    abstract fun toolbarTitle(): String


    fun setToolbarTitle() {
        activity?.let {
            if (it is BaseNavigationActivity) {
                (it as MainActivity).apply {
                    toolbar_main?.let {
                        it.postDelayed({
                            TransitionManager.beginDelayedTransition(it, AutoTransition())
                            app_bar_title?.text = this@BaseFragment.toolbarTitle()
                        }, 600)
                    }
                }
            }
        }
    }
}