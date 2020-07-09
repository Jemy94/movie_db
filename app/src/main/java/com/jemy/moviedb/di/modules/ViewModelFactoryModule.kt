package com.jemy.moviedb.di.modules

import com.jemy.moviedb.data.repository.DetailsRepository
import com.jemy.moviedb.data.repository.PopularRepository
import com.jemy.moviedb.ui.fragments.detailsfragment.DetailsViewModelFactory
import com.jemy.moviedb.ui.fragments.popularfragment.PopularViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun getPopularViewModelFactory(
        popularRepository: PopularRepository
    ): PopularViewModelFactory = PopularViewModelFactory(popularRepository)

    @Provides
    @Singleton
    fun getDetailsViewModelFactory(
        detailsRepository: DetailsRepository
    ): DetailsViewModelFactory = DetailsViewModelFactory(detailsRepository)
}