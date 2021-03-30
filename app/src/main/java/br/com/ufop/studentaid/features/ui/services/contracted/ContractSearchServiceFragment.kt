package br.com.ufop.studentaid.features.ui.services.contracted

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.empty
import br.com.ufop.studentaid.core.extensions.gone
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.extensions.visible
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.ProfessionalServiceAdapter
import br.com.ufop.studentaid.features.models.FirestoreUser
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import br.com.ufop.studentaid.features.ui.services.ServicesViewModel
import kotlinx.android.synthetic.main.contracted_services_fragment.contracted_services_recycler
import kotlinx.android.synthetic.main.search_services_fragment.*

class ContractSearchServiceFragment : BaseFragment(R.layout.contract_search_services_fragment),
    SearchView.OnQueryTextListener {


    private lateinit var serviceViewModel: ServicesViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun toolbarTitle(): String = "Selecionar serviço"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setUpViewModels()
        setAdapter()
        setSearchClickListener()
        setClickListener()
        showProgress()
    }

    private fun showProgress() {
        contracted_services_recycler?.gone()
        search_services_progress?.visible()
    }
    private fun hideProgress() {
        search_services_progress?.gone()
        contracted_services_recycler?.visible()
    }

    private fun setClickListener() {
        new_service_btn?.gone()
    }

    private fun setSearchClickListener() {
        search_service_search_view.setOnQueryTextListener(this)
    }

    val adapter = ProfessionalServiceAdapter(arrayListOf()) {user, it ->
        handleClickItem(user, it)
    }

    private fun handleClickItem(it: FirestoreUser,service :String) {
        serviceViewModel.setContrServiceSelected(service)
        findNavController().navigate(R.id.profileFragment, Bundle().apply {
            putParcelable(getString(R.string.PROFILE_CLICKED), it)
        })
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
            mainViewModel = ViewModelProvider(it).get(MainViewModel::class.java)
            observe()
        }
    }

    var listService = arrayListOf<FirestoreUser>()
    private fun observe() {
        mainViewModel.listFirestoreUsers.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            serviceViewModel.fetchProvidedServicesList(it)
            listService = it
            adapter.addAll(it)
            hideProgress()
        })
//        serviceViewModel.providedServicesList.observe(viewLifecycleOwner, Observer {
//            listService = it
//            adapter.addAll(it)
//            hideProgress()
//        })
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
//            val filtered =
//                adapter.list.filter {
//                    it.providedServices!!.contains(query.toLowerCase())
//                }
//            filtered.let {
//                adapter.search(it)
//            }
        } else
            resetAdapter()
    }
    private fun resetAdapter() {
        listService.let {
            adapter.addAll(it)
        }
    }
}