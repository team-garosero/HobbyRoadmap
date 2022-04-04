package com.garosero.android.hobbyroadmap.mainfrags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.adapters.CategoryAdapter
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
            add(RoadmapItem("roadmapTitle1", "date1", 70))
            add(RoadmapItem("roadmapTitle2", "date2", 20))
        }
        dataset.add(CategoryItem("title1", items1))

        var items2 = mutableListOf<RoadmapItem>()
        items2.apply {
            add(RoadmapItem("roadmapTitle1", "date1", 50))
            add(RoadmapItem("roadmapTitle2", "date2", 30))
            add(RoadmapItem("roadmapTitle3", "date3", 10))
        }
        dataset.add(CategoryItem("title2", items2))

        binding.recycler.adapter = CategoryAdapter(dataset)
        return binding.root
    }
}