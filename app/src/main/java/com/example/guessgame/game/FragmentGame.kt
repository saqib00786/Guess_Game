package com.example.guessgame.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.guessgame.R
import com.example.guessgame.databinding.FragmentGameBinding

class FragmentGame : Fragment(R.layout.fragment_game) {

    private var mBinding: FragmentGameBinding? = null
    private val binding get() = mBinding!!
    private val model: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mBinding?.nextBtnIDGame?.setOnClickListener {
            model.onCorrect()
            updateWordText()
            updateScoreText()
        }
        mBinding?.skipBtnIDGame?.setOnClickListener {
            model.onSkip()
            updateWordText()
            updateScoreText()
        }

        updateScoreText()
        updateWordText()

    }


    private fun gameFinished() {
        val score = model.mScore.toString()
        val action = FragmentGameDirections.actionFragmentGameToFragmentScore(score)
        findNavController().navigate(action)
    }

    private fun updateWordText() {
        mBinding?.wordGame?.text = "\"${model.mWord}\""
    }

    private fun updateScoreText() {
        mBinding?.currentScoreIDGame?.text = "Score: ${model.mScore}"
    }

}
