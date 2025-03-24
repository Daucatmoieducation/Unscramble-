package com.example.unscramble.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.allWords
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String
    private var timerJob: Job? = null
    private var isTimeOut: Boolean = false
    private var isPaused = false
    private var score_increase = SCORE_INCREASE

    init {
        resetGame()
    }
    //fun
    fun usedTime(){
        if (_uiState.value.typeGame != GameDifficulty.EASY) {
            startTime()
        }
    }

    fun resetGame() {
        val timeLimit = when (_uiState.value.typeGame) {
        GameDifficulty.HARD -> 13
        GameDifficulty.MEDIUM -> 17
        else -> 1
    }
        val hintsAvailable = when (_uiState.value.typeGame) {
            GameDifficulty.HARD -> 3
            GameDifficulty.MEDIUM -> 5
            else -> 7
        }

        usedWords.clear()
        val firstScrambledWord = pickRandomWordAndShuffle()
        val firstWordMeaning = allWords.find { it.word == currentWord }?.meaning ?: ""

        _uiState.value = GameUiState(
            currentScrambledWord = firstScrambledWord,
            currentWordMeaning = firstWordMeaning,
            hintNumbers = hintsAvailable,
            typeGame = _uiState.value.typeGame,
            remainingTime = timeLimit
        )
        usedTime()
    }

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            var bonusScore = 0
            if(_uiState.value.typeGame != GameDifficulty.EASY){
                val remainingTime = _uiState.value.remainingTime
                bonusScore = when(_uiState.value.typeGame){
                    GameDifficulty.MEDIUM-> remainingTime*2
                    else ->(remainingTime*4.5).toInt()
                }
            }

            val updatedScore = _uiState.value.score.plus(score_increase).plus(bonusScore)
            updateGameState(updatedScore)
            usedTime()
        }
        else if(isTimeOut &&userGuess.equals(currentWord, ignoreCase = true) ){
            var updatedScore = _uiState.value.score.plus(score_increase)
            updateGameState(updatedScore)
            usedTime()
        }
        else if(isTimeOut &&!(userGuess.equals(currentWord, ignoreCase = true)) ){
            val updatedScore = _uiState.value.score
            updateGameState(updatedScore)
            usedTime()
        }
        else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
        isTimeOut = false
    }


    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
        usedTime()
    }

    fun pauseGame() {
        isPaused = true
        timerJob?.cancel()
    }

    fun resumeGame() {
        if (isPaused) {
            startTime() // Tiếp tục đếm ngược thay vì reset
        }
    }

    fun startTime(){
        timerJob?.cancel() // Hủy bộ đếm cũ nếu có

        val timeLimit = when (_uiState.value.typeGame) {
            GameDifficulty.HARD -> 13
            GameDifficulty.MEDIUM -> 17
            else -> 1
        }

        val remainingTime = _uiState.value.remainingTime // Giữ lại thời gian hiện tại

        // Nếu game đang tạm dừng, không reset lại thời gian
        val startTime = if (isPaused) remainingTime else timeLimit

        isPaused = false // Đánh dấu là game đã tiếp tục

        timerJob = viewModelScope.launch {
            for (time in startTime downTo 0) {
                _uiState.update { it.copy(remainingTime = time) }
                delay(1000L)
            }
            isTimeOut = true
            checkUserGuess()
        }
    }





    fun generateHint(): String {
        val word = currentWord
        val revealCount = (word.length * 0.6).toInt() // 60% số ký tự
        val revealedIndexes = word.indices.shuffled().take(revealCount).toSet()

        return word.mapIndexed { index, char ->
            if (index in revealedIndexes) char else '_'
        }.joinToString("")
    }

    fun useHint() {
        _uiState.update { currentState ->
            if (currentState.hintNumbers > 0) {
                currentState.copy(
                    hint = generateHint(),
                    hintNumbers = currentState.hintNumbers - 1
                )
            } else {
                currentState
            }
        }
    }

    fun useSuperHint() {
        _uiState.update { currentState ->
            currentState.copy(
                isSuperHintUsed = true
            )
        }
        score_increase = (SCORE_INCREASE*0.5).toInt()
    }


    fun chosenType(gameDifficulty: GameDifficulty){
        val timeLimit = when (gameDifficulty) {
            GameDifficulty.HARD -> 10
            GameDifficulty.MEDIUM -> 15
            else -> 1
        }
        _uiState.update { currentState->
            currentState.copy(
                typeGame = gameDifficulty,
                remainingTime = timeLimit,
            )
        }
        Log.d("GameViewModel", "chosenType: ${gameDifficulty}")
        Log.d("GameViewModel", "Current gameType after update: $uiState.value.typeGame")
        resetGame()
    }

    //end fun

    private fun updateGameState(updatedScore: Int) {
        if (_uiState.value.currentWordCount >= MAX_NO_OF_WORDS) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true,
                    hint = "",
                )
            }
        } else {
            val nextScrambledWord = pickRandomWordAndShuffle()
            val nextWordMeaning = allWords.find { it.word == currentWord }?.meaning ?: ""

            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = nextScrambledWord,
                    currentWordMeaning = nextWordMeaning,
                    currentWordCount = currentState.currentWordCount + 1,
                    score = updatedScore,
                    hint = "",
                )
            }
        }
    }


    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        val unusedWords = allWords.filter { it.word !in usedWords }
        if (unusedWords.isEmpty()) return ""

        val selectedWord = unusedWords.random()
        currentWord = selectedWord.word
        usedWords.add(currentWord)

        return shuffleCurrentWord(currentWord)
    }
}
