package com.garosero.android.hobbyroadmap.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerMyroadmapBinding

/**
 * Adapter for the [RecyclerView] in [CategoryAdapter].
 */

class MyRoadmapAdapter(var dataset : MutableList<RoadmapItem>)
    : RecyclerView.Adapter<MyRoadmapAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerMyroadmapBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    inner class ViewHolder(private val binding : RecyclerMyroadmapBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item : RoadmapItem){
            binding.apply {
                tvTitle.text = item.title
                tvDate.text = item.date
                tvPercentage.text = "${item.percentage}%"
            }
        }
    }
}