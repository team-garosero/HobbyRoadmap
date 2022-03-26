package com.garosero.android.hobbyroadmap.mainfrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.FragmentAdventureBinding

class AdventureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAdventureBinding.inflate(inflater, container, false)
        return binding.root
    }
}