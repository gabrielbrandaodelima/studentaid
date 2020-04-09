package br.com.ufop.studentaid.core.platform

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.app_bar_layout.*

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    abstract fun toolbarTitle(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbarTitle()
    }

    private fun setToolbarTitle() {
        activity?.let {
            if (it is BaseActivity) {
                toolbar_main?.let {
                    TransitionManager.beginDelayedTransition(it, AutoTransition())
                    app_bar_title?.text = toolbarTitle()
                }
            }
        }
    }
}