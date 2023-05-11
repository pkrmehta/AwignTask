package com.pkdev.awigntask.di

import com.pkdev.awigntask.network.RepoAPI
import com.pkdev.awigntask.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesRepoAPI(retrofit: Retrofit): RepoAPI{
        return retrofit.create(RepoAPI::class.java)
    }

}