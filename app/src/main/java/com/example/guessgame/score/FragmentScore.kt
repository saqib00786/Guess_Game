package com.example.guessgame.score

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guessgame.databinding.FragmentScoreBinding

class FragmentScore : Fragment() {

    private val args:FragmentScoreArgs by navArgs()

    private  var mBinding : FragmentScoreBinding ?= null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentScoreBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.scoreIDScore?.text  = args.mScore
        mBinding?.playAgainBtnIDScore?.setOnClickListener {
            onPlayAgain()
        }
    }

    private fun onPlayAgain() {
        val action = FragmentScoreDirections.actionFragmentScoreToFragmentTitle()
        findNavController().navigate(action)
    }
}