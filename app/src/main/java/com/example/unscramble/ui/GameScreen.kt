package com.example.unscramble.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.R
import com.example.unscramble.model.GameDifficulty
import com.example.unscramble.model.GameViewModel
import com.example.unscramble.model.TimerBar
import com.example.unscramble.ui.theme.UnscrambleTheme

@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    modifier: Modifier = Modifier

) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val difficulty: GameDifficulty =gameUiState.typeGame
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    var showExitDialog by remember { mutableStateOf(false) }

    LaunchedEffect(showExitDialog) {
        if (showExitDialog) {
            gameViewModel.pauseGame()  // Dừng game
        } else {
            gameViewModel.resumeGame() // Tiếp tục game
        }
    }
    BackHandler(enabled = true) {
        showExitDialog = true
    }
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Xác nhận thoát") },
            text = { Text("Bạn có chắc chắn muốn thoát khỏi trò chơi?") },
            confirmButton = {
                Button(onClick = {
                    showExitDialog = false
                    navController.popBackStack() // Quay về màn hình trước đó
                }) {
                    Text("Thoát")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showExitDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
    Column(
        modifier = modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Chế độ: ${gameUiState.typeGame}")
        if (difficulty != GameDifficulty.EASY) {
            val maxTime = when (gameUiState.typeGame) {
                GameDifficulty.HARD -> 15
                GameDifficulty.MEDIUM -> 20
                else -> 1
            }
            TimerBar(
                remainingTime = gameUiState.remainingTime,
                maxTime = maxTime,
                modifier = Modifier.padding(mediumPadding)
            )
        }
        if (gameUiState.hintNumbers > 0 || gameUiState.isSuperHintUsed == false) {
            AssistanceBar(
                hintNumber = gameUiState.hintNumbers,
                isSuperHintUsed = gameUiState.isSuperHintUsed,
                usedHint = { gameViewModel.useHint() },
                usedSuperHint = { gameViewModel.useSuperHint() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = mediumPadding)
            )
        }
        Log.d("GameScreen", "Recomposing GameScreen: score = ${gameUiState.score}")
        GameLayout(
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            wordCount = gameUiState.currentWordCount,
            userGuess = gameViewModel.userGuess,
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            currentScrambledWord = gameUiState.currentScrambledWord,
            wordMeaning = gameUiState.currentWordMeaning,
            isGuessWrong = gameUiState.isGuessedWordWrong,
            hint = gameUiState.hint,
            score = gameUiState.score,
            isSuperHintUsed = gameUiState.isSuperHintUsed,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = mediumPadding, start = mediumPadding, end = mediumPadding)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { gameViewModel.checkUserGuess() }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = { gameViewModel.skipWord() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 16.sp
                )
            }
        }


        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() },
                pauseGame = {gameViewModel.pauseGame()},
                navController = navController
            )
        }
    }
}

@Composable
fun AssistanceBar(
    hintNumber: Int,
    isSuperHintUsed: Boolean,
    usedHint: () -> Unit,
    usedSuperHint: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiển thị Siêu trợ giúp (🔥) nếu còn lượt sử dụng
        if (isSuperHintUsed ==false) {
            AssistanceIcon(
                onClicked = usedSuperHint,
                icon = R.drawable.baseline_local_fire_department_24
            )
        } else {
            Spacer(modifier = Modifier.size(48.dp)) // Giữ khoảng trống khi mất icon
        }

        Spacer(modifier = Modifier.weight(1f)) // Đẩy iconHint sang phải

        // Hiển thị Gợi ý (💡) nếu còn lượt sử dụng
        if (hintNumber > 0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${hintNumber}x", style = typography.titleLarge)
                AssistanceIcon(
                    onClicked = usedHint,
                    icon = R.drawable.baseline_lightbulb_24
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp)) // Giữ khoảng trống khi mất icon
        }
    }
}

@Composable
fun AssistanceIcon(
    @DrawableRes icon : Int,
    onClicked: ()->Unit,
    modifier: Modifier = Modifier
){

    IconButton(
        onClick = onClicked,
        modifier = modifier.size(50.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Help",
            tint = Color.Unspecified,
        )
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Log.d("GameViewModel", "chosenType: $score")
        Text(
            text = stringResource(R.string.score, score),
            style = typography.titleLarge,
            modifier = Modifier
        )
    }
}


@Composable
fun GameLayout(
    currentScrambledWord: String,
    score: Int,
    wordMeaning:String,
    wordCount: Int,
    isGuessWrong: Boolean,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    hint: String,
    isSuperHintUsed: Boolean,
    modifier: Modifier = Modifier
) {
    val gameViewModel: GameViewModel = viewModel()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val gameUiState by gameViewModel.uiState.collectAsState()
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                GameStatus(
                    score = score,
                    modifier = Modifier.weight(1f)

                )

                Text(
                    modifier = Modifier
                        .clip(shapes.medium)
                        .background(colorScheme.surfaceTint)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    text = stringResource(R.string.word_count, wordCount),
                    style = typography.titleMedium,
                    color = colorScheme.onPrimary
                )
            }

            Text(
                text = currentScrambledWord,
                style = typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
            if (isSuperHintUsed ==true){
                Text(
                    text =wordMeaning,
                    textAlign = TextAlign.Center,
                    style = typography.titleMedium,
                    fontStyle = FontStyle.Italic
                )
            }
            Text(
                text = hint,
            )

            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserGuessChanged,
                label = {
                    if (isGuessWrong) {
                        Text(stringResource(R.string.wrong_guess))
                    } else {
                        Text(stringResource(R.string.enter_your_word))
                    }
                },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }
}


//show score
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    pauseGame: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.congratulations)) },
        text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    navController.popBackStack("home_screen", false) // Quay về HomeScreen
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        val gameViewModel: GameViewModel = viewModel()
        val navController = rememberNavController()
        GameScreen(gameViewModel = gameViewModel, navController = navController)
    }
}
