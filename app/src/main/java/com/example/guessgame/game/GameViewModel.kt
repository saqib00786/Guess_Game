package com.example.guessgame.game

import android.database.DatabaseUtils
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(0, 200, 100, 300)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the time when the phone will start buzzing each second
        private const val COUNTDOWN_PANIC_SECONDS = 10L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 30000L
    }

    private val counDownTimer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The String version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val _mWord = MutableLiveData<String>()
    val mWord: LiveData<String>
        get() = _mWord

    // todo: restriction for mScore not access out of viewModel
    private val _mScore = MutableLiveData<Int>()
    val mScore: LiveData<Int>
        get() = _mScore

    // todo: for EVENT game finish code
    private val _onGameFinished = MutableLiveData<Boolean>()
    val onGameFinished: LiveData<Boolean>
        get() = _onGameFinished

    // Todo: Event that triggers the phone to buzz using different patterns, determined by BuzzType
    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    private lateinit var mWordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        _mScore.value = 0
//        _mWord.value = ""
        _onGameFinished.value = false
        resetList()
        nextWord()
        counDownTimer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished.div(ONE_SECOND))
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventBuzz.value = BuzzType.GAME_OVER
                _onGameFinished.value = true
            }
        }.start()

    }


    private fun resetList() {
        mWordList = mutableListOf(
            "Apple",
            "Ball",
            "Cap",
            "Doll",
            "Egg",
            "Frog",
            "Gate",
            "Hen",
            "Iron",
            "Jug",
            "Kite",
            "Lemon",
            "NoteBook",
            "Orange",
            "Pen",
            "Queen",
            "Rose",
            "Star",
            "Tractor",
            "Umbrella",
            "Van",
            "Wrong",
            "Yolk",
            "Zebra"
        )
        mWordList.shuffle()
    }

    private fun nextWord() {
        if (mWordList.isEmpty()) {
            resetList()
        }
        _mWord.value = mWordList.removeAt(0)
    }

    fun onSkip() {
        nextWord()
    }

    fun onCorrect() {
        _mScore.value = mScore.value?.plus(1)
        _eventBuzz.value = BuzzType.CORRECT
        nextWord()
    }

    fun onCompeleteGameFinished() {
        _onGameFinished.value = false

    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    override fun onCleared() {
        super.onCleared()
        counDownTimer.cancel()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}