package com.garosero.android.hobbyroadmap.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.NoticeItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerNoticeBinding

/**
 * Adapter for the [RecyclerView] in [NoticeFragment].
 */

class NoticeAdapter(
        var dataset : MutableList<NoticeItem> = mutableListOf())
    : RecyclerView.Adapter<NoticeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeAdapter.ViewHolder {
        val view = RecyclerNoticeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: NoticeAdapter.ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    class ViewHolder(private val binding: RecyclerNoticeBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: NoticeItem){
            with (binding){
                tvTitle.text = item.title
                // todo-날짜 처리
                tvDate.text = item.date.toString()
            }
        }
    }
}