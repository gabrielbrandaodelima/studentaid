package br.com.ufop.studentaid.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.features.models.ServiceModel
import kotlinx.android.synthetic.main.item_service.view.*

/**
 * Created by Use Mobile on 06/05/2019.
 */
class ServiceModelAdapter(
    private val list: ArrayList<ServiceModel>,
    private val clickListener: (ServiceModel) -> Unit
) : RecyclerView.Adapter<ServiceModelAdapter.ViewHolder>() {


    fun clear() {
        val size = list.size
        list.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addAll(itemList: List<ServiceModel>) {
        val startindex = list.size
        list.addAll(startindex, itemList)
        notifyItemRangeInserted(startindex, itemList.size)
    }

    fun add(item: ServiceModel) {
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
        fun bindView(service: ServiceModel, clickListener: (ServiceModel) -> Unit) {

            with(view) {

                serviceTxt.text = service.name

                setOnClickListener {
                    clickListener(service)
                }
            }
        }
    }
}