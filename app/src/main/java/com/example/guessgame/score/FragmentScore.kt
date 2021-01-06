package com.example.guessgame.score

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guessgame.databinding.FragmentScoreBinding

class FragmentScore : Fragment() {

    private val args:FragmentScoreArgs by navArgs()

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

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

        viewModelFactory = ScoreViewModelFactory(args.mScore)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ScoreViewModel::class.java)

        mBinding!!.scoreViewModel = viewModel
        mBinding!!.lifecycleOwner = this

        viewModel.eventPlayAgain.observe(viewLifecycleOwner,{ isFinish ->
            if(isFinish){
                val action = FragmentScoreDirections.actionFragmentScoreToFragmentTitle()
                findNavController().navigate(action)
                viewModel.onCompeletePlayAgain()
            }
        })

        return binding.root
    }

}