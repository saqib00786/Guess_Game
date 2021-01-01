package com.example.guessgame.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var mWord = ""
    var mScore = 0
    lateinit var mWordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        resetList()
        nextWord()
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
           // gameFinished()
        } else {
            mWord = mWordList.removeAt(0)
        }
    }

    fun onSkip() {
        nextWord()
    }

    fun onCorrect() {
        mScore++
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}