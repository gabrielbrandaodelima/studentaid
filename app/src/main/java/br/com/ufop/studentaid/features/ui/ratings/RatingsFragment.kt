package br.com.ufop.studentaid.features.ui.ratings

import android.os.Bundle
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment

class RatingsFragment : BaseFragment(R.layout.ratings_fragment) {


    override fun toolbarTitle(): String = getString(R.string.text_ratings)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()

    }

}