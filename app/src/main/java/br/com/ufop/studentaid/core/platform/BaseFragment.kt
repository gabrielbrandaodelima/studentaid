package br.com.ufop.studentaid.core.platform

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

}