package com.garosero.android.hobbyroadmap.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.CategoryItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerPreviewBinding

/**
 * Adapter for the [RecyclerView] in [HomeFragment].
 */

class HomeAdapter(var dataset : MutableList<CategoryItem>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerPreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    class ViewHolder(private val binding : RecyclerPreviewBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: CategoryItem){
            binding.apply {
                // todo : add image
            }
        }
    }
}