package com.jemy.moviedb.ui.fragments.popularfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.jemy.moviedb.App
import com.jemy.moviedb.R
import com.jemy.moviedb.data.common.ResourceState.ERROR
import com.jemy.moviedb.data.common.ResourceState.SUCCESS
import com.jemy.moviedb.data.common.ResourceState.LOADING
import com.jemy.moviedb.data.response.PopularResponse.Popular
import com.jemy.moviedb.di.component.AppComponent
import com.jemy.moviedb.di.component.DaggerAppComponent
import com.jemy.moviedb.ui.fragments.popularfragment.adapter.PopularAdapter
import com.jemy.moviedb.utils.Constants.Error.GENERAL
import kotlinx.android.synthetic.main.fragment_popular.*
import javax.inject.Inject

class PopularFragment : Fragment() {

    @Inject
    lateinit var popularViewModelFactory: PopularViewModelFactory
    private val viewModel: PopularViewModel by lazy {
        ViewModelProvider(this, popularViewModelFactory)
            .get(PopularViewModel::class.java)
    }

    lateinit var component: AppComponent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_popular, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInjection()
        viewModel.getPopular()
        getPopular()
        observePopular(view)
    }

    private fun setupInjection() {
        component = DaggerAppComponent.builder()
            .build()
        component.inject(this)
    }

    private fun getPopular() {
        viewModel.getPopular()
    }

    private fun observePopular(view: View) {
        viewModel.popular.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.state) {
                LOADING -> popularProgressBar.visibility = View.VISIBLE
                SUCCESS -> {
                    popularProgressBar.visibility = View.GONE
                    resource.data?.let { popular ->
                        val popularList = popular.results
                        Log.d("Popular", "${popularList.size}")
                        if (popularList.isNotEmpty()) {
                            val adapter = PopularAdapter(popularList)
                            popularRecycler.adapter = adapter
                            adapter.setItemCallBack { id ->
                                val bundle = bundleOf("popularId" to id)
                                view.findNavController().navigate(
                                    R.id.action_popularFragment_to_detailsFragment,
                                    bundle
                                )
                            }
                        }
                    }
                }
                ERROR -> {
                    popularProgressBar.visibility = View.GONE
                    resource.message?.let { msg ->
                        when (msg) {
                            GENERAL -> Toast.makeText(
                                activity,
                                getString(R.string.general_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } ?: Toast.makeText(
                        activity,
                        getString(R.string.general_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun setupPopularAdapter(popularList: List<Popular>) {

    }


}