package com.pkdev.awigntask.network

import com.pkdev.awigntask.models.RepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoAPI {

    @GET("repositories")
    suspend fun getRepositories(@Query("q") query: String, @Query("per_page") perPage: String, @Query("page") page: String): Response<RepoResponse>

}