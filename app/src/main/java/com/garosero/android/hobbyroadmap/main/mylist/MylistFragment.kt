package com.garosero.android.hobbyroadmap.main.mylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.models.CategoryItem
import com.garosero.android.hobbyroadmap.models.MyRoadmapItem

class MylistFragment : Fragment() {
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var root:View
    lateinit var recycler: RecyclerView

    val dataset = mutableListOf<CategoryItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_mylist, container, false)
        recycler = root.findViewById(R.id.recycler)
        categoryAdapter = CategoryAdapter(root.context)

        initRecycler()
        return root
    }

    private fun initRecycler(){
        recycler.adapter = categoryAdapter
        dataset.apply {
            var roadmap = mutableListOf<MyRoadmapItem>()
            roadmap.apply {
                add(MyRoadmapItem("플렌테리어", "2021.11.12", 0.13f))
                add(MyRoadmapItem("독서", "2022.02.22", 0.12f))
            }

            add(CategoryItem("카테고리1", roadmap))
            add(CategoryItem("카테고리2", roadmap))
            add(CategoryItem("카테고리3", roadmap))

            categoryAdapter.dataset = dataset
            categoryAdapter.notifyDataSetChanged()
        }
    }
}