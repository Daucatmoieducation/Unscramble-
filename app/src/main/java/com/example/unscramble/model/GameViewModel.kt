package com.example.unscramble.model

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
    init {
        resetGame()
    }

    //fun
    fun resetGame() {
        usedWords.clear()
        val firstScrambledWord = pickRandomWordAndShuffle() // Lấy từ đầu tiên
        val firstWordMeaning = allWords[currentWord] ?: ""  // Lấy nghĩa của từ đầu tiên

        _uiState.value = GameUiState(
            currentScrambledWord = firstScrambledWord,
            currentWordMeaning = firstWordMeaning
        )
        startTime()
    }

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }


    fun checkUserGuess() {
//        timerJob?.cancel()
        if (userGuess.equals(currentWord, ignoreCase = true)) {

            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
            startTime()
        }
        else if(isTimeOut &&userGuess.equals(currentWord, ignoreCase = true) ){
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
            startTime()
        }
        else if(isTimeOut &&!(userGuess.equals(currentWord, ignoreCase = true)) ){
            val updatedScore = _uiState.value.score
            updateGameState(updatedScore)
            startTime()
        }
        else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
//        startTime()
        isTimeOut = false
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
        startTime()
    }

    fun startTime(){
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            for (time in 10 downTo 0) {
                _uiState.update { it.copy(remainingTime = time) }
                delay(1000L) // Chờ 1 giây
            }
            isTimeOut = true
            checkUserGuess()
        }
    }

    //end fun

    private fun updateGameState(updatedScore: Int) {
        val nextScrambledWord = pickRandomWordAndShuffle()
        val nextWordMeaning = allWords[currentWord] ?: ""
        if (usedWords.size == MAX_NO_OF_WORDS) {

            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = nextScrambledWord,
                    currentWordMeaning = nextWordMeaning,  // Hiển thị nghĩa đúng
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
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
        val unusedWords = allWords.keys - usedWords  // Lấy danh sách từ chưa dùng
        if (unusedWords.isEmpty()) return "" // Tránh lỗi khi hết từ

        currentWord = unusedWords.random() // Chọn từ ngẫu nhiên
        usedWords.add(currentWord) // Đánh dấu từ đã dùng
        return shuffleCurrentWord(currentWord) // Trả về từ đã xáo trộn
    }

}
