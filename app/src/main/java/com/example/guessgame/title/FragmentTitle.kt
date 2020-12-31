package com.example.guessgame.title

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.guessgame.databinding.FragmentTitleBinding

class FragmentTitle : Fragment() {
    private var mBinding: FragmentTitleBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTitleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playBtnIDTitle.setOnClickListener {
            findNavController().navigate(FragmentTitleDirections.actionFragmentTitleToFragmentGame())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}