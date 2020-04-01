package br.com.ufop.studentaid.core.extensions

import android.view.View


/** Created by Gabriel
 * on 05/07/2019
 * at 16:08
 * at Usemobile
 **/


fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visibility(b: Boolean) {
    this.visibility = if (b) View.VISIBLE else View.GONE
}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
