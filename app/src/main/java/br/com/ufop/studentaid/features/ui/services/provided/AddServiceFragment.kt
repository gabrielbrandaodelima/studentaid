package br.com.ufop.studentaid.features.ui.services.provided

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.AddServiceModelAdapter
import br.com.ufop.studentaid.features.ui.login.LoginViewModel
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import br.com.ufop.studentaid.features.ui.services.ServicesViewModel
import kotlinx.android.synthetic.main.add_service_fragment.*

class AddServiceFragment : BaseFragment(R.layout.add_service_fragment) {


    private lateinit var serviceViewModel: ServicesViewModel

    val adapter = AddServiceModelAdapter(arrayListOf()) { handleClickListener() }

    private fun handleClickListener() {

    }

    override fun toolbarTitle(): String = "Ofertar Serviço"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setUpViewModels()
        setAdapter()
        setClickListener()
    }

    private fun setAdapter() {
        add_service_recycler_view?.setUpRecyclerView(null,{

            it.adapter = adapter
        })
    }

    private fun setClickListener() {
        add_type_service_tv?.setOnClickListener {
            findNavController().navigate(R.id.searchServiceFragment,Bundle().apply {

            })
        }
        add_service_fab?.setOnClickListener {
            createAlertDialog("Confirma o serviço?","",{},{})
        }
    }
    private fun setUpViewModels() {
        activity?.let {
            serviceViewModel = ViewModelProvider(it).get(ServicesViewModel::class.java)
            observe()
        }
    }

    private fun observe() {
        serviceViewModel.serviceSelected.observe(viewLifecycleOwner, Observer {

            adapter.add(it)
        })
    }
}