package com.jemy.moviedb.ui.fragments.detailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jemy.moviedb.R
import com.jemy.moviedb.di.component.AppComponent
import com.jemy.moviedb.di.component.DaggerAppComponent
import com.jemy.moviedb.ui.fragments.popularfragment.PopularViewModel
import com.jemy.moviedb.ui.fragments.popularfragment.PopularViewModelFactory
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var popularId: Long?=0

    @Inject
    lateinit var detailsViewModelFactory:DetailsViewModelFactory
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this, detailsViewModelFactory)
            .get(DetailsViewModel::class.java)
    }
   private lateinit var component: AppComponent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInjection()
        getPopularIdFromArguments()
    }

    private fun getPopularIdFromArguments() {
        popularId = arguments?.getLong("popularId")
    }
    private fun setupInjection() {
        component = DaggerAppComponent.builder()
            .build()
        component.inject(this)
    }
}