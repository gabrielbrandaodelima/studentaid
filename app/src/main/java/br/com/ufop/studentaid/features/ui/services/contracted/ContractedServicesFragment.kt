package br.com.ufop.studentaid.features.ui.services.contracted

import android.os.Bundle
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.ServiceModelAdapter
import br.com.ufop.studentaid.features.models.ServiceModel
import br.com.ufop.studentaid.features.util.MockUtils
import kotlinx.android.synthetic.main.contracted_services_fragment.*

class ContractedServicesFragment : BaseFragment(R.layout.contracted_services_fragment) {


    override fun toolbarTitle(): String = getString(R.string.text_contracted_services)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setAdapter()
    }

    private fun setAdapter() {
        contracted_services_recycler?.setUpRecyclerView(requireContext(),{
            it.adapter = ServiceModelAdapter(arrayListOf(),{

            })
        })
    }

}