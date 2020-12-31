package com.example.guessgame.game

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.guessgame.R
import com.example.guessgame.databinding.FragmentGameBinding
import com.example.guessgame.databinding.FragmentTitleBinding

class FragmentGame : Fragment(R.layout.fragment_game) {

    private var mBinding: FragmentGameBinding? = null
    private val binding get() = mBinding!!

    private var mWord = ""
    private var mScore = 0
    private lateinit var mWordList : MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetList()
        nextWord()

        mBinding?.nextBtnIDGame?.setOnClickListener {
            onCorrect()
        }
        mBinding?.skipBtnIDGame?.setOnClickListener {
            onSkip()
        }

        updateScoreText()
        updateWordText()

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

    private fun gameFinished(){
        val score = mScore.toString()
        val action = FragmentGameDirections.actionFragmentGameToFragmentScore(score)
        findNavController().navigate(action)
    }

    private fun updateWordText() {
        mBinding?.wordGame?.text = "\"${mWord}\""
    }

    private fun updateScoreText() {
        mBinding?.currentScoreIDGame?.text = "Score: ${mScore.toString()}"
    }

    private fun onSkip() {
        nextWord()
    }

    private fun onCorrect() {
        mScore++
        nextWord()
    }

    private fun nextWord() {
        if(mWordList.isEmpty()){
            gameFinished()
        }else{
            mWord = mWordList.removeAt(0)
        }

        updateWordText()
        updateScoreText()
    }





}
