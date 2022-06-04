package com.garosero.android.hobbyroadmap.main.adventure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.databinding.FragmentAdventureBinding

class AdventureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdventureBinding.inflate(inflater, container, false)

        binding.btnInfo.setOnClickListener {
            with(binding.tvExplain){
                visibility = if (visibility==View.GONE) View.VISIBLE else View.GONE
            }
        }
        return binding.root
    }
}