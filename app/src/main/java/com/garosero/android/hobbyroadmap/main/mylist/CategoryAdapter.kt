package com.garosero.android.hobbyroadmap.main.mylist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.models.CategoryItem

class CategoryAdapter(private val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var dataset = mutableListOf<CategoryItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_category, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tv_title:TextView = view.findViewById(R.id.tv_title)

        fun bind(item: CategoryItem){
            tv_title.text = item.title
        }
    }
}