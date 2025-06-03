package com.example.jsonphapitomaszkaczor.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jsonphapitomaszkaczor.viewmodel.MainViewModel

import com.example.jsonphapitomaszkaczor.data.repository.MainRepository
import com.example.jsonphapitomaszkaczor.data.network.RetrofitInstance
import com.example.jsonphapitomaszkaczor.viewmodel.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory())
    val posts by viewModel.posts.collectAsState()
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("JSONPlaceholder App") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("profile")
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        }) { innerPadding ->
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text("Error: $error")
        } else {


            LazyColumn(
                        contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
                ) {
                items(posts.size) { index ->
                    val post = posts[index]
                    val user = users.find { it.id == post.userId }
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .systemBarsPadding()
                            .clickable {
                                navController.navigate("postDetail/${post.id}")
                            }
                    ) {
                        Text(text = post.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "✍ by ${user?.name ?: "Unknown"} ❤\uFE0F",
                            modifier = Modifier.clickable {
                                navController.navigate("userDetail/${post.userId}")
                            }
                        )
                        IconButton(onClick = { navController.navigate("profile") }) {
                            Icon(Icons.Default.Person, contentDescription = "Profile")
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}