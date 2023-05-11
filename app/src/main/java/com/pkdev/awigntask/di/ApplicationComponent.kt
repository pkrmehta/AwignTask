package com.pkdev.awigntask.di

import com.pkdev.awigntask.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

}