package com.example.jsonphapitomaszkaczor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jsonphapitomaszkaczor.data.repository.MainRepository


class PostDetailViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostDetailViewModel(repository) as T
    }
}