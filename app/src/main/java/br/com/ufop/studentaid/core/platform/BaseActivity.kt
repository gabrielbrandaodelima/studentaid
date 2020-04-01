package br.com.ufop.studentaid.core.platform

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes contentLayoutId: Int = 0) : AppCompatActivity(contentLayoutId) {

}