package com.example.unscramble.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.unscramble.R
import com.example.unscramble.model.GameDifficulty
import com.example.unscramble.model.GameViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        ClassifyButton(
            typeRes = R.string.easy,
            onClicked = {
                gameViewModel.chosenType(GameDifficulty.EASY)
                navController.navigate("game_screen") // Chuyển sang GameScreen
            }
        )
        ClassifyButton(
            typeRes = R.string.medium,
            onClicked = {
                gameViewModel.chosenType(GameDifficulty.MEDIUM)
                navController.navigate("game_screen") // Chuyển sang GameScreen
            }
        )
        ClassifyButton(
            typeRes = R.string.hard,
            onClicked = {
                gameViewModel.chosenType(GameDifficulty.HARD)
                navController.navigate("game_screen") // Chuyển sang GameScreen
            }
        )
    }
}


@Composable
fun ClassifyButton(
    @StringRes typeRes: Int,
    onClicked:()->Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClicked,
    ) {
        Text(stringResource(typeRes))
    }
}