package com.garosero.android.hobbyroadmap.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.CategoryItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerMylistParentBinding

/**
 * Adapter for the [RecyclerView] in [MylistFragment].
 */

class MylistParentAdapter(
    var dataset : MutableList<CategoryItem> = mutableListOf())
    : RecyclerView.Adapter<MylistParentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerMylistParentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    class ViewHolder(private val binding : RecyclerMylistParentBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: CategoryItem){
            binding.apply {
                tvTitle.text = item.title
                recycler.adapter = MylistChildAdapter(item.roadmapList)
            }
        }
    }
}