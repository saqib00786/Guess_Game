package com.example.guessgame.game

import android.database.DatabaseUtils
import android.media.AudioAttributes
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guessgame.R
import com.example.guessgame.databinding.FragmentGameBinding

class FragmentGame : Fragment() {

    private var mBinding: FragmentGameBinding? = null
    private val binding get() = mBinding!!

    private lateinit var model : GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentGameBinding.inflate(inflater, container, false)

        model = ViewModelProvider(this)
            .get(GameViewModel::class.java)

        mBinding?.gameViewModel = model
        mBinding?.lifecycleOwner = this

        model.onGameFinished.observe(viewLifecycleOwner, {
            if (it) {
                val score = model.mScore.value ?: 0
                val action = FragmentGameDirections.actionFragmentGameToFragmentScore(score)
                findNavController().navigate(action)
                model.onCompeleteGameFinished()
            }
        })

        // Buzzes when triggered with different buzz events
        model.eventBuzz.observe(viewLifecycleOwner, { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                model.onBuzzComplete()
            }
        })

        return binding.root
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern,-1)
            }
        }
    }
}
