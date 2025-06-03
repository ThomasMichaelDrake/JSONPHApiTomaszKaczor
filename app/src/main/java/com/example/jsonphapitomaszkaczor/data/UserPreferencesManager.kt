package com.example.jsonphapitomaszkaczor.yourprofile

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferencesManager(private val context: Context) {
    companion object {
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val IMAGE_PATH = stringPreferencesKey("image_path")
    }

    val firstNameFlow = context.dataStore.data.map { it[FIRST_NAME] ?: "" }
    val lastNameFlow = context.dataStore.data.map { it[LAST_NAME] ?: "" }
    val imagePathFlow = context.dataStore.data.map { it[IMAGE_PATH] ?: "" }

    suspend fun saveUserData(firstName: String, lastName: String, imagePath: String) {
        context.dataStore.edit {
            it[FIRST_NAME] = firstName
            it[LAST_NAME] = lastName
            it[IMAGE_PATH] = imagePath
        }
    }
}
