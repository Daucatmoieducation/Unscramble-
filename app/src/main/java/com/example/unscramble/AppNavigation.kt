package com.example.unscramble

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.model.GameViewModel
import com.example.unscramble.ui.GameScreen
import com.example.unscramble.ui.HomeScreen
import com.example.unscramble.ui.LoginScreen
import com.example.unscramble.ui.SignupScreen

@Preview(showBackground = true)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()
    NavHost(navController = navController, startDestination = "home_screen") {

        composable("home_screen"){
            HomeScreen(
                navController = navController,
                gameViewModel = gameViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
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
        composable("signup_screen"){
            SignupScreen(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}