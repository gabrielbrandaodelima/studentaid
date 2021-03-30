package br.com.ufop.studentaid.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.gone
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.features.models.FirestoreUser
import br.com.ufop.studentaid.features.models.ProfessionalServiceModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_contract_service.view.*
import kotlinx.android.synthetic.main.item_service.view.*
import kotlinx.android.synthetic.main.item_service.view.item_service_name
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfessionalServiceAdapter(
    val list: ArrayList<FirestoreUser>,
    private val clickListener: (FirestoreUser, String) -> Unit
) : RecyclerView.Adapter<ProfessionalServiceAdapter.ViewHolder>() {


    fun clear() {
        val size = list.size
        list.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addAll(itemList: List<FirestoreUser>) {
        val startindex = list.size
        list.addAll(startindex, itemList.filter { it.providedServices.isNullOrEmpty().not() })
        notifyItemRangeInserted(startindex, itemList.size)
    }

    fun add(item: FirestoreUser) {
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
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_contract_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            bindView(item, clickListener)
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val item_lytw = view.item_contract_lyt
        val serviceProf = view.item_service_professional
        val serviceImgProf = view.item_service_professional_img
        val serviceRecycler = view.item_recycler
        fun bindView(user: FirestoreUser, clickListener: (FirestoreUser, String) -> Unit) {

            with(view) {
                user.providedServices?.let { services ->
                    if (services.isNotEmpty()) {

                        serviceProf.text = user.name
                        user.photoUrl?.let {
                            if (it.isNotBlank()) {
                                Picasso.get().load(it).into(serviceImgProf)
                            }
                        }
                        serviceRecycler.setUpRecyclerView(context, {
                            it.adapter = ServiceModelAdapter(services) {
                                clickListener(user, it)
                            }

                        })
                    } else {
                        item_lytw.gone()
                    }


                } ?: item_lytw.gone()
//                setOnClickListener {
//                    clickListener(user)
//                }
            }
        }
    }
}