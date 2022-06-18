package com.garosero.android.hobbyroadmap.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.garosero.android.hobbyroadmap.databinding.FragmentHomeBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
    private val model = HomeViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {
            binding.recycler.layoutManager = GridLayoutManager(context, 2)
            binding.recycler.adapter = HomeAdapter(model.roadmapList)
        }

        return binding.root
    }
}