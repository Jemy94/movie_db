package com.jemy.moviedb

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.jemy.moviedb.di.component.AppComponent
import com.jemy.moviedb.di.component.DaggerAppComponent
import com.jemy.moviedb.di.modules.AppModule

class App : Application() {

//    companion object {
//
//        fun get(activity: FragmentActivity): App {
//            return activity.application as App
//        }
//    }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        buildComponent()
    }

    private fun buildComponent() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
    }
}