package com.example.unscramble.ui

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.R
import com.example.unscramble.model.GameDifficulty
import com.example.unscramble.model.GameViewModel
import com.example.unscramble.ui.theme.UnscrambleTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    var showDifficultyOptions by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AppBar(navController = navController)
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 380.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayButton(
                onClick = { showDifficultyOptions = !showDifficultyOptions }
            )

            AnimatedVisibility(
                visible = showDifficultyOptions,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                    initialOffsetY = { -it }
                ),
                exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
                    targetOffsetY = { -it }
                )
            ) {
                DifficultyOptions(navController, gameViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController, modifier: Modifier = Modifier) {
    val auth = Firebase.auth
    var user by remember { mutableStateOf<FirebaseUser?>(auth.currentUser) }
    var showLogoutDialog by remember { mutableStateOf(false) } // Trạng thái hiển thị dialog

    DisposableEffect(auth) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            user = firebaseAuth.currentUser
        }
        auth.addAuthStateListener(listener)

        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    // Xử lý tên hiển thị
    val displayName = user?.displayName?.takeIf { it.isNotEmpty() }
        ?: user?.email?.substringBefore("@") // Lấy phần trước dấu "@"

    TopAppBar(
        title = { Text(text = "") },
        actions = {
            if (displayName != null) {
                Text(
                    text = "Hi, $displayName",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { showLogoutDialog = true }, // Khi nhấn vào tên, hiển thị dialog
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                IconButton(onClick = { navController.navigate("login_screen") }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "User Icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        modifier = modifier
    )

    // Dialog xác nhận đăng xuất
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Đăng xuất") },
            text = { Text("Bạn có chắc chắn muốn đăng xuất không?") },
            confirmButton = {
                TextButton(onClick = {
                    auth.signOut()
                    user = null
                    showLogoutDialog = false
                    navController.navigate("home_screen")
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}


@Composable
fun PlayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(200.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF205781),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Play",
            fontSize = 18.sp
        )
    }
}

@Composable
fun DifficultyOptions(
    navController: NavController,
    gameViewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        ClassifyText(
            typeRes = R.string.easy,
            color = Color(0xFF4CAF50),
            onClicked = {
                gameViewModel.chosenType(GameDifficulty.EASY)
                navController.navigate("game_screen")
            }
        )
        ClassifyText(
            typeRes = R.string.medium,
            color = Color(0xFFFFA000),
            onClicked = {
                gameViewModel.chosenType(GameDifficulty.MEDIUM)
                navController.navigate("game_screen")
            }
        )
        ClassifyText(
            typeRes = R.string.hard,
            color = Color(0xFFD32F2F),
            onClicked = {
                gameViewModel.chosenType(GameDifficulty.HARD)
                navController.navigate("game_screen")
            }
        )
    }
}

@Composable
fun ClassifyText(
    @StringRes typeRes: Int,
    color: Color,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(typeRes),
        color = color,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 20.dp)
            .clickable(onClick = onClicked) // Biến Text thành nút bấm
    )
}



@Preview(showBackground = true)
@Composable
fun PreviewHome(){
    UnscrambleTheme {
        val navController = rememberNavController()
        val gameViewModel: GameViewModel = viewModel()
//                    AppNavigation()
//        GameScreen(navController,gameViewModel)
        HomeScreen(navController,gameViewModel)
    }
}