package br.com.ufop.studentaid.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.features.models.ServiceModel
import kotlinx.android.synthetic.main.item_add_service.view.*
import kotlinx.android.synthetic.main.item_service.view.*

/**
 * Created by Use Mobile on 06/05/2019.
 */
class AddServiceModelAdapter(
    private val list: ArrayList<String>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<AddServiceModelAdapter.ViewHolder>() {


    fun clear() {
        val size = list.size
        list.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun getServicesList(): ArrayList<String> {
        val listServices = arrayListOf<String>()
        list.forEach {
            it?.let { it1 -> listServices.add(it1) }
        }
        return listServices
    }

    fun addAll(itemList: List<String>) {
        val startindex = list.size
        list.addAll(startindex, itemList)
        notifyItemRangeInserted(startindex, itemList.size)
    }

    fun add(item: String) {
        if (list.contains(item).not()) {
            val index = list.size
            list.add(index, item)
            notifyItemInserted(index)
        }
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            bindView(item, clickListener)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val serviceTxt = view.item_add_service
        fun bindView(service: String, clickListener: (Int) -> Unit) {

            with(view) {

                serviceTxt.text = service

                setOnClickListener {
                    clickListener(adapterPosition)
                }
            }
        }
    }
}