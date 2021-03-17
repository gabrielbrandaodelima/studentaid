package br.com.ufop.studentaid.features.ui.services.provided

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.ServiceModelAdapter
import br.com.ufop.studentaid.features.models.ServiceModel
import br.com.ufop.studentaid.features.ui.login.LoginViewModel
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import br.com.ufop.studentaid.features.ui.services.ServicesViewModel
import br.com.ufop.studentaid.features.util.MockUtils
import kotlinx.android.synthetic.main.add_service_fragment.*
import kotlinx.android.synthetic.main.contracted_services_fragment.*
import kotlinx.android.synthetic.main.provided_services_fragment.*
import kotlinx.android.synthetic.main.provided_services_fragment.provided_services_recycler

class SearchServiceFragment : BaseFragment(R.layout.search_services_fragment) {


    private lateinit var serviceViewModel: ServicesViewModel

    override fun toolbarTitle(): String = "Selecionar serviço"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setUpViewModels()
        setAdapter()
    }

    private fun setAdapter() {
        contracted_services_recycler?.setUpRecyclerView(requireContext(), {
            it.adapter =
                ServiceModelAdapter(MockUtils.getContractedServices() as ArrayList<ServiceModel>) {
                    serviceViewModel.setServiceSelected(it)
                    findNavController().popBackStack()
                }
        })


    }

    private fun setUpViewModels() {
        activity?.let {
            serviceViewModel = ViewModelProvider(it).get(ServicesViewModel::class.java)
        }
    }
}