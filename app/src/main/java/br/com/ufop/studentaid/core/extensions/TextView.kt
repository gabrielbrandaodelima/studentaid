package br.com.ufop.studentaid.core.extensions

import android.widget.TextView
import androidx.core.content.ContextCompat
import br.com.ufop.studentaid.R


fun TextView.setEnable(b: Boolean) {
    this.apply {
        if (b) {
            isEnabled = true
            setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        } else {
            isEnabled = false
            setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }
    }

}