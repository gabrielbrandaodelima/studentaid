package br.com.ufop.studentaid.core.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import br.com.ufop.studentaid.core.platform.Event


/** Created by Gabriel
 * on 17/10/2019
 * at 11:38
 * at Usemobile
 **/

inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}