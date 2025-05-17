package com.example.jsonphapitomaszkaczor.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jsonphapitomaszkaczor.viewmodel.UserDetailViewModel
import com.example.jsonphapitomaszkaczor.data.repository.MainRepository
import com.example.jsonphapitomaszkaczor.data.network.RetrofitInstance
import com.example.jsonphapitomaszkaczor.viewmodel.UserDetailViewModelFactory
import androidx.compose.ui.platform.LocalContext
import com.example.jsonphapitomaszkaczor.data.models.User
import com.example.jsonphapitomaszkaczor.data.network.ApiService
import com.example.jsonphapitomaszkaczor.viewmodel.PostDetailViewModel
import com.example.jsonphapitomaszkaczor.viewmodel.PostDetailViewModelFactory

@Composable
fun UserDetailScreen(navController: NavController, userId: Int) {
    val context = LocalContext.current
    val viewModel: UserDetailViewModel = viewModel(
        factory = UserDetailViewModelFactory(MainRepository(RetrofitInstance.api))
    )
    val user by viewModel.user.collectAsState()
    val todos by viewModel.todos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(userId) {
        viewModel.fetchUserData(userId)
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        user?.let {
            Column(Modifier.padding(22.dp).systemBarsPadding()) {
                Text(text = it.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "👨‍💻 @${it.username}")
                Text(text = "📫 ${it.email}")
                Text(text = "☎️ ${it.phone}")
                Text(text = "🌎 ${it.website}")
                Text(text = "🏢 ${it.company.name}")
                Text(text = "🧭 ${it.address.street}, ${it.address.city}")
                Spacer(Modifier.height(22.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back")
                }
                Text("Tasks:", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(todos.size) { index ->
                        val todo = todos[index]
                        Text(text = "${if (todo.completed) "✅" else "❌"} ${todo.title}")
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        } ?: Text("User Not Found.")
    }
}