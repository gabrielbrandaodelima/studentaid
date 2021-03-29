package br.com.ufop.studentaid.features.ui.services.provided

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.empty
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.ServiceModelAdapter
import br.com.ufop.studentaid.features.models.ServiceModel
import br.com.ufop.studentaid.features.ui.login.LoginViewModel
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import br.com.ufop.studentaid.features.ui.modal.ModalFragment
import br.com.ufop.studentaid.features.ui.services.ServicesViewModel
import br.com.ufop.studentaid.features.util.MockUtils
import kotlinx.android.synthetic.main.add_service_fragment.*
import kotlinx.android.synthetic.main.contracted_services_fragment.*
import kotlinx.android.synthetic.main.contracted_services_fragment.contracted_services_recycler
import kotlinx.android.synthetic.main.provided_services_fragment.*
import kotlinx.android.synthetic.main.provided_services_fragment.provided_services_recycler
import kotlinx.android.synthetic.main.search_services_fragment.*

class SearchServiceFragment : BaseFragment(R.layout.search_services_fragment),
    SearchView.OnQueryTextListener {


    private lateinit var serviceViewModel: ServicesViewModel

    override fun toolbarTitle(): String = "Selecionar serviço"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setUpViewModels()
        setAdapter()
        setSearchClickListener()
        setClickListener()
    }

    private fun setClickListener() {
        new_service_btn?.setOnClickListener {
            val fm: FragmentManager = parentFragmentManager
                ?: activity!!.supportFragmentManager
            fm.let { it1 -> ModalFragment.newInstance {


                adapter.add(it)

                handleClickItem(it)
            }.show(it1,"")}

        }
    }

    private fun setSearchClickListener() {
        search_service_search_view.setOnQueryTextListener(this)
    }

    val adapter = ServiceModelAdapter(MockUtils.getListServices()) {
        handleClickItem(it)
    }

    private fun handleClickItem(it: String) {
        serviceViewModel.setServiceSelected(it)
        findNavController().popBackStack()
    }

    private fun setAdapter() {
        contracted_services_recycler?.setUpRecyclerView(requireContext(), {
            it.adapter =
                adapter
        })


    }

    private fun setUpViewModels() {
        activity?.let {
            serviceViewModel = ViewModelProvider(it).get(ServicesViewModel::class.java)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
//        query?.let { viewModel.searchPokemonDetail(it) }

        if (query.isNullOrBlank()) {
            resetAdapter()
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filter(newText)
        return false
    }

    var querySearch = String.empty()
    private fun filter(query: String?) {
        querySearch = query ?: String.empty()
        if (query != null && query.isNotEmpty()) {
            val filtered =
                adapter.list.filter {
                    it.toLowerCase().contains(query.toLowerCase())
                }
            filtered.let {
                adapter.search(it)
            }
        } else
            resetAdapter()
    }
    private fun resetAdapter() {
        MockUtils.getListServices().let {
            adapter.addAll(it)
        }
    }
}