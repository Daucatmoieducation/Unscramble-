package com.example.unscramble.model

data class GameUiState(
    val currentScrambledWord: String = "",
    val currentWordCount: Int = 1,
    val currentWordMeaning: String = "",
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false,
    val remainingTime: Int = 15,
    val hint: String = "",
    val hintNumbers: Int = 0,
    val isSuperHintUsed : Boolean = false,
    val typeGame: GameDifficulty = GameDifficulty.EASY
)


enum class GameDifficulty {
    EASY,
    MEDIUM,
    HARD
}