package com.garosero.android.hobbyroadmap.main.mylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.models.CategoryItem

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
            add(CategoryItem("1"))
            add(CategoryItem("2"))
            add(CategoryItem("3"))

            categoryAdapter.dataset = dataset
            categoryAdapter.notifyDataSetChanged()
        }
    }
}