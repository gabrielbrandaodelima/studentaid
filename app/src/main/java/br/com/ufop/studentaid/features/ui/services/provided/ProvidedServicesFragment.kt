package br.com.ufop.studentaid.features.ui.services.provided

import android.os.Bundle
import androidx.lifecycle.Observer
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
import kotlinx.android.synthetic.main.provided_services_fragment.*

class ProvidedServicesFragment : BaseFragment(R.layout.provided_services_fragment) {


    private lateinit var serviceViewModel: ServicesViewModel
    private lateinit var maniViewModel: MainViewModel

    override fun toolbarTitle(): String = getString(R.string.text_provided_services)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setUpViewModels()
        setAdapter()
        setClickListener()
    }

    private fun setClickListener() {
        add_provided_service_fab?.setOnClickListener {
            findNavController().navigate(R.id.addServiceFragment)

        }
    }

    val adapter = ServiceModelAdapter(arrayListOf()) {

    }

    private fun setAdapter() {
        provided_services_recycler?.setUpRecyclerView(requireContext(), {
            it.adapter = adapter

        })

    }

    private fun setUpViewModels() {
        activity?.let {
            serviceViewModel = ViewModelProvider(it).get(ServicesViewModel::class.java)
            maniViewModel = ViewModelProvider(it).get(MainViewModel::class.java)
            observe()
        }
    }

    private fun observe() {

        maniViewModel.loggedFirestoreUser.observe(viewLifecycleOwner, Observer {
            it.providedServices?.let { it1 -> adapter.addAll(it1) }
        })
    }
}