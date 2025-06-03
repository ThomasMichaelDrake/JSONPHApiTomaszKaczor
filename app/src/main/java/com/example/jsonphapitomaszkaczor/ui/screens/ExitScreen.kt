package com.example.jsonphapitomaszkaczor.ui.screens


import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ExitScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? Activity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount < -100) {
                        // Swipe from left to right
                        navController.navigate("exit")
                    }
                }
            }
            .padding(32.dp)
    ) {
        Button(
            onClick = { activity?.finish() },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(alignment = androidx.compose.ui.Alignment.Center),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Wyjdź z aplikacji", color = Color.White, style = MaterialTheme.typography.titleLarge)
        }
    }
}