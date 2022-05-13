package com.garosero.android.hobbyroadmap.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    val dataset = mutableListOf<RoadmapItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            // todo : add datas
            dataset.apply {
                if (isEmpty()){
                    for (a in 0..5){
                        add(RoadmapItem(
                            title = "title",
                            desc = "설명이 들어갈 자리",
                            timelimit = "2022-12-31"))
                    }
                }
            }
            binding.recycler.layoutManager = GridLayoutManager(context, 2)
            binding.recycler.adapter = HomeAdapter(dataset)
        }

        return binding.root
    }
}