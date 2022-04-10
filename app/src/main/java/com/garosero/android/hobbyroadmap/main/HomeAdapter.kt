package com.garosero.android.hobbyroadmap.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerHomeBinding
import com.garosero.android.hobbyroadmap.syllabus.SyllabusActivity

/**
 * Adapter for the [RecyclerView] in [HomeFragment].
 */

class HomeAdapter(
    var dataset : MutableList<RoadmapItem> = mutableListOf()) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    class ViewHolder(private val binding : RecyclerHomeBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: RoadmapItem){
            binding.apply {
                tvTitle.text = item.title
                tvDesc.text = item.desc
                cardview.setOnClickListener {
                    // goto SyllabusActivity
                    val intent = Intent(itemView.context, SyllabusActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}