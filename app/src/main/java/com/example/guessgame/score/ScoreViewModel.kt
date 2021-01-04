package com.example.guessgame.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore : Int ) : ViewModel(){
    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain : LiveData<Boolean>
    get() = _eventPlayAgain

    private val _mScore = MutableLiveData<Int>()
    val mScore : LiveData<Int>
        get() = _mScore

    init {
        _mScore.value = finalScore
    }

    fun isPlayAgain(){
        _eventPlayAgain.value = true
    }

    fun onCompeletePlayAgain(){
        _eventPlayAgain.value = false
    }
}