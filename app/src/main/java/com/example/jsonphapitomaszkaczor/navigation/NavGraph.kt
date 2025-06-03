package com.example.jsonphapitomaszkaczor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jsonphapitomaszkaczor.ui.screens.ExitScreen
import com.example.jsonphapitomaszkaczor.ui.screens.MainScreen
import com.example.jsonphapitomaszkaczor.ui.screens.PostDetailScreen
import com.example.jsonphapitomaszkaczor.ui.screens.UserDetailScreen
import com.example.jsonphapitomaszkaczor.ui.screens.YourProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "main"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("main") {
            MainScreen(navController)
        }
        composable("postDetail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toInt() ?: 0
            PostDetailScreen(navController, postId)
        }
        composable("userDetail/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            UserDetailScreen(navController, userId)
        }
        composable("profile") {
            YourProfileScreen()
        }
        composable("exit") {
            ExitScreen(navController = navController)
        }

    }
}