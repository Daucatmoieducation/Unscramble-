package com.example.unscramble.model

data class GameUiState(
    val currentScrambledWord: String = "",
    val currentWordCount: Int = 1,
    val currentWordMeaning: String = "",
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false,
    val remainingTime: Int = 15
)
