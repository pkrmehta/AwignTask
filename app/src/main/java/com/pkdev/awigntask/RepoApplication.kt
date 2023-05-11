package com.pkdev.awigntask

import android.app.Application
import com.pkdev.awigntask.di.ApplicationComponent
import com.pkdev.awigntask.di.DaggerApplicationComponent

class RepoApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().build()

    }

}