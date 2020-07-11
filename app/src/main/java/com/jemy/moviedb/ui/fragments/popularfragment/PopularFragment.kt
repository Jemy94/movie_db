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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jemy.moviedb.App
import com.jemy.moviedb.R
import com.jemy.moviedb.data.common.ResourceState.ERROR
import com.jemy.moviedb.data.common.ResourceState.SUCCESS
import com.jemy.moviedb.data.common.ResourceState.LOADING
import com.jemy.moviedb.data.response.PopularResponse.Popular
import com.jemy.moviedb.di.component.AppComponent
import com.jemy.moviedb.di.component.DaggerAppComponent
import com.jemy.moviedb.ui.fragments.popularfragment.adapter.PopularAdapter
import com.jemy.moviedb.utils.Constants
import com.jemy.moviedb.utils.Constants.Error.GENERAL
import com.jemy.moviedb.utils.extensions.toastLong
import kotlinx.android.synthetic.main.fragment_popular.*
import okhttp3.internal.notify
import javax.inject.Inject

class PopularFragment : Fragment() {

    @Inject
    lateinit var popularViewModelFactory: PopularViewModelFactory
    private val viewModel: PopularViewModel by lazy {
        ViewModelProvider(this, popularViewModelFactory)
            .get(PopularViewModel::class.java)
    }

    lateinit var component: AppComponent
    private val adapter = PopularAdapter()
    private var popularList = mutableListOf<Popular>()
    private var page = 1
    private var totalPages = 0
    private val VISIBLE_THRESHOLD = 10
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    private var loading = false
    private var isFirstTime = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_popular, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInjection()
        getPopular()
        observePopular(view)
        setUpInfiniteScroll()
    }

    private fun setupInjection() {
        component = DaggerAppComponent.builder()
            .build()
        component.inject(this)
    }

    private fun getPopular() {
        if (isFirstTime) {
            viewModel.getPopular()
        }
    }

    private fun observePopular(view: View) {
        viewModel.popular.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.state) {
                LOADING -> popularProgressBar.visibility = View.VISIBLE
                SUCCESS -> {
                    popularProgressBar.visibility = View.GONE
                    resource.data?.let { popular ->
                        //   popularList.addAll(popular.results)
                        totalPages = popular.totalPages
                        Log.d("Popular", "${popularList.size}")
                        setupPopularAdapter()
                        adapter.addItems(popular.results)
                        loading = false
                        isFirstTime = false
                        setupOnItemClickListener(view)
                    }
                }
                ERROR -> {
                    popularProgressBar.visibility = View.GONE
                    resource.message?.let { msg ->
                        when (msg) {
                            GENERAL -> requireActivity().toastLong(getString(R.string.general_error))
                        }
                    } ?: requireActivity().toastLong(getString(R.string.general_error))
                }
            }
        })
    }

    private fun setupPopularAdapter() {
        popularRecycler.adapter = adapter
    }

    private fun setupOnItemClickListener(view: View) {
        adapter.setItemCallBack { popular ->
            val bundle = bundleOf(Constants.POPULAR_ID to popular?.id)
            bundle.putString(Constants.POPULAR_NAME, popular?.name)
            bundle.putString(
                Constants.POPULAR_DEPARTMENT,
                popular?.knownForDepartment
            )
            bundle.putDouble(Constants.POPULARITY, popular?.popularity ?: 0.0)
            view.findNavController().navigate(
                R.id.action_popularFragment_to_detailsFragment,
                bundle
            )
        }
    }

    private fun setUpInfiniteScroll() {
        popularRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                totalItemCount = layoutManager.itemCount
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!loading
                    && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)
                    && page <= totalPages
                ) {
                    page++
                    viewModel.getPopular(page)
                    loading = true
                    popularProgressBar.visibility = View.VISIBLE
                }

            }
        })
    }


}