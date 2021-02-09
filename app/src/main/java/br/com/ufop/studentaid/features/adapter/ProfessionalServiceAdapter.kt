package br.com.ufop.studentaid.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.features.models.ProfessionalServiceModel

class ProfessionalServiceAdapter(
    private val list: ArrayList<ProfessionalServiceModel>,
    private val searchText: String,
    private val clickListener: (ProfessionalServiceModel) -> Unit
) : RecyclerView.Adapter<ProfessionalServiceAdapter.ViewHolder>() {


    fun clear() {
        val size = list.size
        list.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addAll(itemList: List<ProfessionalServiceModel>) {
        val startindex = list.size
        list.addAll(startindex, itemList)
        notifyItemRangeInserted(startindex, itemList.size)
    }

    fun add(item: ProfessionalServiceModel) {
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_service , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            bindView(item, clickListener)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(user: ProfessionalServiceModel, clickListener: (ProfessionalServiceModel) -> Unit) {

            with(view) {
            }
        }
    }
}