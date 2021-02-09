package br.com.ufop.studentaid.core.extensions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView?.setUpRecyclerView(
    context: Context?,
    adapterSetCallback: (adapter: RecyclerView) -> Unit,
    orientation: Int = RecyclerView.VERTICAL
) {
    this?.apply {
        setHasFixedSize(true)
        setItemViewCacheSize(20)
        context?.let {
            layoutManager = LinearLayoutManager((it), orientation, false)
        }

        adapterSetCallback(this)
    }
}