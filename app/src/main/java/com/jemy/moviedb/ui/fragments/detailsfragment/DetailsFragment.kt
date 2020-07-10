package com.jemy.moviedb.ui.fragments.detailsfragment

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
import com.jemy.moviedb.R
import com.jemy.moviedb.data.common.ResourceState
import com.jemy.moviedb.di.component.AppComponent
import com.jemy.moviedb.di.component.DaggerAppComponent
import com.jemy.moviedb.ui.fragments.detailsfragment.adapter.ImagesAdapter
import com.jemy.moviedb.ui.fragments.popularfragment.PopularViewModel
import com.jemy.moviedb.ui.fragments.popularfragment.PopularViewModelFactory
import com.jemy.moviedb.ui.fragments.popularfragment.adapter.PopularAdapter
import com.jemy.moviedb.utils.Constants
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_popular.*
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var popularId: Long? = null
    private var popularName: String? = "unknown"
    private var popularDepartment: String? = "unknown"
    private var popularity: Double? = 0.0


    @Inject
    lateinit var detailsViewModelFactory: DetailsViewModelFactory
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
        getPopularImages()
        observePopularImages(view)
    }

    private fun getPopularIdFromArguments() {
        popularId = arguments?.getLong(Constants.POPULAR_ID)
        popularName = arguments?.getString(Constants.POPULAR_NAME)
        nameDetails.text = popularName
        popularDepartment = arguments?.getString(Constants.POPULAR_DEPARTMENT)
        departmentDetails.text = popularDepartment
        popularity = arguments?.getDouble(Constants.POPULARITY)
        popularityDetails.text = popularity.toString()
    }

    private fun setupInjection() {
        component = DaggerAppComponent.builder()
            .build()
        component.inject(this)
    }

    private fun getPopularImages() {
        popularId?.let { viewModel.getImages(it) }
    }

    private fun observePopularImages(view: View) {
        viewModel.popularImages.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.state) {
                ResourceState.LOADING -> detailsProgressBar.visibility = View.VISIBLE
                ResourceState.SUCCESS -> {
                    detailsProgressBar.visibility = View.GONE
                    resource.data?.let { imagesResponse ->
                        val adapter = imagesResponse.profiles?.let { ImagesAdapter(it) }
                        popularImagesGrid.adapter = adapter

                    }
                }
                ResourceState.ERROR -> {
                    detailsProgressBar.visibility = View.GONE
                    resource.message?.let { msg ->
                        when (msg) {
                            Constants.Error.GENERAL -> Toast.makeText(
                                activity,
                                getString(R.string.general_error),
                                Toast.LENGTH_LONG
                            ).show()
                            Constants.Error.NO_DATA -> {
                                popularImagesGrid.visibility = View.GONE
                                noImagesTextView.visibility = View.VISIBLE
                            }

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
}