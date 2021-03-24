package br.com.ufop.studentaid.features.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

class UtilKeyboard {

    companion object {


        @JvmStatic
        fun showKeyboard(context: Context, view: View?) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            view?.let { imm.showSoftInput(it, InputMethodManager.SHOW_FORCED) }
        }

        @JvmStatic
        fun hideKeyboard(context: Context, view: View?) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        interface KeyboardEventListenerInterface {
            fun keyboardIsOpen(isOpen: Boolean)
        }
    }

}