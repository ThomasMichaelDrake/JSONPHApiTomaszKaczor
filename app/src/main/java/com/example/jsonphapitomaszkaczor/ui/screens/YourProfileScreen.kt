package com.example.jsonphapitomaszkaczor.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.jsonphapitomaszkaczor.viewmodel.YourProfileViewModel
import java.io.File

import androidx.compose.material3.*


@Composable
fun YourProfileScreen(viewModel: YourProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var firstName by remember { mutableStateOf(uiState.firstName) }
    var lastName by remember { mutableStateOf(uiState.lastName) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { imagePicker.launch("image/*") }) {
            Text("Select Profile Image")
        }

        Spacer(modifier = Modifier.height(8.dp))

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        } ?: if (uiState.imagePath.isNotBlank()) {
            Image(
                painter = rememberAsyncImagePainter(File(uiState.imagePath)),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        } else {

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val file = imageUri?.let { uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                val file = File(context.filesDir, "profile_image.jpg")
                inputStream?.use { file.outputStream().use { out -> it.copyTo(out) } }
                file
            }
            viewModel.saveData(firstName, lastName, file?.absolutePath ?: "")
        }) {
            Text("Save Profile")
        }
    }
}
