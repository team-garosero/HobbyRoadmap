package com.garosero.android.hobbyroadmap.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.FragmentTilItemBinding

class TilItemFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTilItemBinding.inflate(layoutInflater)
        val view = binding.root

        // onclick
        with(binding){
            btnDelete.setOnClickListener {
                view.findNavController().navigate(R.id.action_tilItemFragment_to_tilListFragment)
            }
            btnSave.setOnClickListener {
                view.findNavController().navigate(R.id.action_tilItemFragment_to_tilListFragment)
            }
        }

        return view
    }
}