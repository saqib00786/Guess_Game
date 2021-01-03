package com.example.guessgame.game

import android.database.DatabaseUtils
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.guessgame.R
import com.example.guessgame.databinding.FragmentGameBinding

class FragmentGame : Fragment() {

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
        }
        mBinding?.skipBtnIDGame?.setOnClickListener {
            model.onSkip()
        }
        model.mScore.observe(viewLifecycleOwner, {
            mBinding?.currentScoreIDGame?.text = "Score: ${it}"
        })

        model.mWord.observe(viewLifecycleOwner,{
            mBinding?.wordGame?.text = "\"${it}\""
        })

        model.currentTime.observe(viewLifecycleOwner,{
            mBinding?.timeCounterIDGame?.text = DateUtils.formatElapsedTime(it)
        })

        model.onGameFinished.observe(viewLifecycleOwner,{
            if(it){
                gameFinished()
                model.onCompeleteGameFinished()
            }
        })
    }


    private fun gameFinished() {
       // val score = model.mScore.toString()
        val action = FragmentGameDirections.actionFragmentGameToFragmentScore((model.mScore.value ?: 0).toString())
        findNavController().navigate(action)
    }

}
