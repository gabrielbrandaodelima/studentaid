package br.com.ufop.studentaid.features.ui.services.contracted

import android.os.Bundle
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment

class ContractedServicesFragment : BaseFragment(R.layout.contracted_services_fragment) {


    override fun toolbarTitle(): String = getString(R.string.text_contracted_services)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()

    }

}