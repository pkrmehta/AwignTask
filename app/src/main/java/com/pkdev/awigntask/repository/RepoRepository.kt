package com.pkdev.awigntask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pkdev.awigntask.models.Item
import com.pkdev.awigntask.network.RepoAPI
import javax.inject.Inject

class RepoRepository @Inject constructor(val api: RepoAPI) {

    private var _repoLiveData = MutableLiveData<List<Item>> ()
    val repoLiveData: LiveData<List<Item>>
    get() = _repoLiveData

    suspend fun getRepoList(query: String){
        val result = api.getRepositories(query, "25", "1")
        if(result.isSuccessful && result.body()!=null){
            _repoLiveData.postValue(result.body()!!.items)
        }
    }

}