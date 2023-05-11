package com.pkdev.awigntask.viewmodels.providers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pkdev.awigntask.repository.RepoRepository
import com.pkdev.awigntask.viewmodels.RepoViewModel
import javax.inject.Inject

class RepoViewModelProvider @Inject constructor(val repository: RepoRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RepoViewModel(repository) as T
    }

}