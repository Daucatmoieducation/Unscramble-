package com.example.unscramble.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.model.GameViewModel

@Preview(showBackground = true)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()
    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
        composable("game_screen") {
            GameScreen(modifier = Modifier.fillMaxSize(),
                navController = navController,
                gameViewModel = gameViewModel,
            )
        }
        composable("home_screen"){
            HomeScreen(
                navController = navController,
                gameViewModel = gameViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
        composable("signup_screen"){
            SignupScreen(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}