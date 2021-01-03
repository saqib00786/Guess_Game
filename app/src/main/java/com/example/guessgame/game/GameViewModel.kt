package com.example.guessgame.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    private val counDownTimer: CountDownTimer
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

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

    private lateinit var mWordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        _mScore.value = 0
        _mWord.value = ""
        _onGameFinished.value = false
        resetList()
        nextWord()
        counDownTimer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished.div(ONE_SECOND))
            }

            override fun onFinish() {
                _currentTime.value = DONE
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
        nextWord()
    }

    fun onCompeleteGameFinished() {
        _onGameFinished.value = false

    }

    override fun onCleared() {
        super.onCleared()
        counDownTimer.cancel()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}