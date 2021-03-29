package br.com.ufop.studentaid.features.ui.services.contracted

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.ServiceModelAdapter
import br.com.ufop.studentaid.features.models.ServiceModel
import br.com.ufop.studentaid.features.util.MockUtils
import kotlinx.android.synthetic.main.contracted_services_fragment.*
import kotlinx.android.synthetic.main.provided_services_fragment.*

class ContractedServicesFragment : BaseFragment(R.layout.contracted_services_fragment) {


    override fun toolbarTitle(): String = "Serviços Contratados"


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setAdapter()
        setClickListener()
    }

    private fun setClickListener() {
        contract_service_fab?.setOnClickListener {
            findNavController().navigate(R.id.contractSearchServiceFragment)

        }
    }
    private fun setAdapter() {
        contracted_services_recycler?.setUpRecyclerView(requireContext(),{
            it.adapter = ServiceModelAdapter(arrayListOf(),{

            })
        })
    }

}