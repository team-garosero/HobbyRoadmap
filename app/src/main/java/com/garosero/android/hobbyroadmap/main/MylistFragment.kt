package com.garosero.android.hobbyroadmap.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.data.CategoryItem
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.databinding.FragmentMylistBinding

class MylistFragment : Fragment() {
    private val dataset = mutableListOf<CategoryItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMylistBinding.inflate(inflater, container, false)

        // todo : input data
        var items1 = mutableListOf<RoadmapItem>()
        items1.apply {
            add(RoadmapItem(title = "roadmapTitle1", timelimit="time limit1"))
            add(RoadmapItem(title = "roadmapTitle1", timelimit="time limit1"))
        }
        dataset.add(CategoryItem("title1", items1))

        var items2 = mutableListOf<RoadmapItem>()
        items2.apply {
            add(RoadmapItem(title = "roadmapTitle2", timelimit="time limit2"))
            add(RoadmapItem(title = "roadmapTitle2", timelimit="time limit2"))
            add(RoadmapItem(title = "roadmapTitle2", timelimit="time limit2"))
        }
        dataset.add(CategoryItem("title2", items2))

        binding.recycler.adapter = MylistParentAdapter(dataset)
        return binding.root
    }
}