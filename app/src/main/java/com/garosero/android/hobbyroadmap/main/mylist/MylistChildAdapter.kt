package com.garosero.android.hobbyroadmap.main.mylist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.CategoryItem
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerMylistChildBinding
import com.garosero.android.hobbyroadmap.main.helper.ActionConfig
import com.garosero.android.hobbyroadmap.syllabus.SyllabusActivity

/**
 * Adapter for the [RecyclerView] in [MylistParentAdapter].
 */

class MylistChildAdapter(var dataset : MutableList<RoadmapItem>)
    : RecyclerView.Adapter<MylistChildAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerMylistChildBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    inner class ViewHolder(private val binding : RecyclerMylistChildBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item : RoadmapItem){
            binding.apply {
                tvTitle.text = item.title
                tvDate.text = item.last_access
                tvPercentage.text = "${item.percentage}%"
                layout.setOnClickListener {
                    // goto SyllabusActivity
                    val intent = Intent(itemView.context, SyllabusActivity::class.java)
                    intent.setAction(ActionConfig.ACTION_MYLIST_TO_ROADMAP)
                    intent.putExtra(ActionConfig.ROADMAP_ITEM, item)
                    itemView.context.startActivity(intent)
                }

                /*// todo 이미지 링크 포함하도록 db 구조 변경
                // 이미지가 없으면, 이미지뷰가 보이지 않도록 처리해두었음
                if (item.imageLink == null){
                    ivRoadmap.visibility = View.GONE
                } else {
                    ivRoadmap.visibility = View.VISIBLE
                }*/
            }
        }
    }
}