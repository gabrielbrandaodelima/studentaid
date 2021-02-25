package br.com.ufop.studentaid.features.ui.services.provided

import android.os.Bundle
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.ServiceModelAdapter
import br.com.ufop.studentaid.features.models.ServiceModel
import br.com.ufop.studentaid.features.util.MockUtils
import kotlinx.android.synthetic.main.provided_services_fragment.*

class ProvidedServicesFragment : BaseFragment(R.layout.provided_services_fragment) {


    override fun toolbarTitle(): String = getString(R.string.text_provided_services)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setAdapter()
    }

    private fun setAdapter() {
        provided_services_recycler?.setUpRecyclerView(requireContext(),{
            it.adapter = ServiceModelAdapter(MockUtils.getProvidedServices() as ArrayList<ServiceModel>,{

            })
        })

    }

}