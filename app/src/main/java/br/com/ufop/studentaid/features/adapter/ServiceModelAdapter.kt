package br.com.ufop.studentaid.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.features.models.ProfessionalServiceModel
import br.com.ufop.studentaid.features.models.ServiceModel
import kotlinx.android.synthetic.main.item_service.view.*

/**
 * Created by Use Mobile on 06/05/2019.
 */
class ServiceModelAdapter(
        val list: ArrayList<String>,
        private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<ServiceModelAdapter.ViewHolder>() {

    init {
        if (list.isEmpty()) {
            list.add("Nenhum serviço")
        } else {
            list.remove("Nenhum serviço")
        }
    }

    fun getListContracted(): ArrayList<String> {
        list.remove("Nenhum serviço")
        return list
    }
    fun clear() {
        val size = list.size
        list.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addAll(itemList: List<String>) {
        itemList.isNotEmpty().let {
            if (it) {
                list.remove("Nenhum serviço")
                val startindex = list.size
                list.addAll(startindex, itemList)
                val auxList = list.distinct()
                list.clear()
                list.addAll(auxList)
                notifyItemRangeInserted(startindex, auxList.size)
            }
        }
    }
    fun search(itemList: List<String>) {
        clear()
        list.addAll(itemList)
        notifyDataSetChanged()
    }
    fun add(item: String) {
        val index = list.size
        list.add(index, item)
        notifyItemInserted(index)
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
                LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            bindView(item, clickListener)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val serviceTxt = view.item_service_name
        fun bindView(service: String, clickListener: (String) -> Unit) {

            with(view) {

                serviceTxt.text = service

                setOnClickListener {
                    clickListener(service)
                }
            }
        }
    }
}