package com.pkdev.awigntask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkdev.awigntask.models.Item
import com.pkdev.awigntask.repository.RepoRepository
import kotlinx.coroutines.launch

class RepoViewModel(private val repository: RepoRepository): ViewModel() {

    val repoLiveData: LiveData<List<Item>>
    get() = repository.repoLiveData

    fun getRepoList(query: String){
        viewModelScope.launch {
            repository.getRepoList(query)
        }
    }

}