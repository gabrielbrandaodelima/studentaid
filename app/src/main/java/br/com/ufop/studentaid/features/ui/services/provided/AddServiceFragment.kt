package br.com.ufop.studentaid.features.ui.services.provided

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.AddServiceModelAdapter
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import br.com.ufop.studentaid.features.ui.services.ServicesViewModel
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_PROVIDED_SERVICES
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.add_service_fragment.*

class AddServiceFragment : BaseFragment(R.layout.add_service_fragment) {


    private lateinit var serviceViewModel: ServicesViewModel
    private lateinit var mainViewModel: MainViewModel

    val adapter = AddServiceModelAdapter(arrayListOf()) { handleClickListener(it) }

    private fun handleClickListener(i: Int) {

        adapter.removeAt(i)
    }

    override fun toolbarTitle(): String = getString(R.string.title_add_service)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setUpViewModels()
        setAdapter()
        setClickListener()
    }

    private fun setAdapter() {
        add_service_recycler_view?.setUpRecyclerView(null, {

            it.adapter = adapter
        })
    }

    private fun setClickListener() {
        add_type_service_tv?.setOnClickListener {
            findNavController().navigate(R.id.searchServiceFragment)
        }
        add_service_fab?.setOnClickListener {
            createAlertDialog("Confirma o serviço?", "", {
                if (adapter.itemCount == 0) {
                    Toast.makeText(context, "Insira pelo menos 1 serviço", Toast.LENGTH_SHORT).show()
                } else {

//                    userLoginReference.update(KEY_SERVICES, adapter.getServicesList())
//                            .addOnCompleteListener { task ->
//                                if (task.isSuccessful) {
//
//                                    try {
//
//                                        showMessage("Sucesso")
//                                    } catch (exception: Exception) {
//                                        showMessage("catch succesful")
//                                    }
//                                } else {
//                                    // Handle failures
//                                    // ...
                    var userServices = mainViewModel.getLoggedFirestoreUser()?.providedServices
                    if (userServices.isNullOrEmpty()) {
                        userServices = adapter.getServicesList()
                    } else {
                        userServices.addAll(adapter.getServicesList())
                    }
                    userLoginReference.set(mapOf(KEY_PROVIDED_SERVICES to userServices.toSet().toList()), SetOptions.merge())
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    showMessage("Sucesso")
                                    mainViewModel.getLoggedFirestoreUser()?.providedServices?.addAll(adapter.getServicesList())
                                    findNavController().popBackStack()
                                } else {
                                    showMessage("Ocorreu um erro ao salvar o serviço , tente novamente")
                                }

                            }
//                                    showMessage("Ocorreu um erro ao salvar o arquivo , tente novamente")
//                                }
//                            }
                }
            }, {})
        }
    }

    private fun setUpViewModels() {
        activity?.let {
            serviceViewModel = ViewModelProvider(it).get(ServicesViewModel::class.java)
            mainViewModel = ViewModelProvider(it).get(MainViewModel::class.java)
            observe()
        }
    }

    private fun observe() {
        serviceViewModel.serviceSelected.observe(viewLifecycleOwner, Observer {

            adapter.add(it)
        })
    }
}