package com.example.jsonphapitomaszkaczor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.example.jsonphapitomaszkaczor.yourprofile.UserPreferencesManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class YourProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val imagePath: String = ""
)

class YourProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = UserPreferencesManager(application)

    private val _uiState = MutableStateFlow(YourProfileUiState())
    val uiState: StateFlow<YourProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                preferences.firstNameFlow,
                preferences.lastNameFlow,
                preferences.imagePathFlow
            ) { first, last, image ->
                YourProfileUiState(first, last, image)
            }.collect { _uiState.value = it }
        }
    }

    fun saveData(firstName: String, lastName: String, imagePath: String) {
        viewModelScope.launch {
            preferences.saveUserData(firstName, lastName, imagePath)
        }
    }
}
