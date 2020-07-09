package com.jemy.moviedb.di.component

import com.jemy.moviedb.App
import com.jemy.moviedb.di.modules.ApiModule
import com.jemy.moviedb.di.modules.AppModule
import com.jemy.moviedb.di.modules.NetworkModule
import com.jemy.moviedb.di.modules.ViewModelFactoryModule
import com.jemy.moviedb.ui.fragments.detailsfragment.DetailsFragment
import com.jemy.moviedb.ui.fragments.popularfragment.PopularFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        NetworkModule::class,
        ViewModelFactoryModule::class,
        ApiModule::class]
)
interface AppComponent {

    fun inject(app: App)

    fun inject(popularFragment: PopularFragment)

    fun inject(detailsFragment: DetailsFragment)
}