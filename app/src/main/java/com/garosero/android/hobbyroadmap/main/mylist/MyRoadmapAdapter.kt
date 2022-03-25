package com.garosero.android.hobbyroadmap.main.mylist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.models.MyRoadmapItem

class MyRoadmapAdapter(private val context: Context)
    : RecyclerView.Adapter<MyRoadmapAdapter.ViewHolder>() {
    var dataset = mutableListOf<MyRoadmapItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRoadmapAdapter.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_myroadmap, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: MyRoadmapAdapter.ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_date = view.findViewById<TextView>(R.id.tv_date)
        val tv_percentage = view.findViewById<TextView>(R.id.tv_percentage)

        fun bind(item : MyRoadmapItem){
            tv_title.text = item.title
            tv_date.text = item.date
            tv_percentage.text = (item.percentage*100).toInt().toString()
        }
    }
}