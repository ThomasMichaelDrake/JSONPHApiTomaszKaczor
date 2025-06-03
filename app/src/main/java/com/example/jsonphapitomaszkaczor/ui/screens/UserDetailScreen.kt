package com.example.jsonphapitomaszkaczor.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jsonphapitomaszkaczor.viewmodel.UserDetailViewModel
import com.example.jsonphapitomaszkaczor.viewmodel.UserDetailViewModelFactory
import com.example.jsonphapitomaszkaczor.data.repository.MainRepository
import com.example.jsonphapitomaszkaczor.data.network.RetrofitInstance
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun UserLocationMap(lat: Double, lng: Double) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(lat, lng), 10f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        cameraPositionState = cameraPositionState
    ) {
        Marker(state = MarkerState(position = LatLng(lat, lng)))
    }
}

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
            Column(
                Modifier
                    .padding(22.dp)
                    .systemBarsPadding()
            ) {
                Text(text = it.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "👨‍💻 @${it.username}")
                Text(text = "📫 ${it.email}")
                Text(text = "☎️ ${it.phone}")
                Text(text = "🌎 ${it.website}")
                Text(text = "🏢 ${it.company.name}")
                Text(text = "🧭 ${it.address.street}, ${it.address.city}")


                val lat = it.address.geo.lat.toDoubleOrNull() ?: 0.0
                val lng = it.address.geo.lng.toDoubleOrNull() ?: 0.0
                Spacer(modifier = Modifier.height(16.dp))
                Text("Lokalizacja na mapie:")
                UserLocationMap(lat = lat, lng = lng)

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
            }
        } ?: Text("User Not Found.")
    }
}
