package com.example.jsonphapitomaszkaczor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jsonphapitomaszkaczor.data.repository.MainRepository
import com.example.jsonphapitomaszkaczor.data.network.RetrofitInstance

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(MainRepository(RetrofitInstance.api)) as T
    }
}