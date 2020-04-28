package br.com.ufop.studentaid.features.ui.services.provided

import android.os.Bundle
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment

class ProvidedServicesFragment : BaseFragment(R.layout.provided_services_fragment) {


    override fun toolbarTitle(): String = this::class.java.simpleName


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()

    }

}