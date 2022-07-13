package com.garosero.android.hobbyroadmap.notice

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.NoticeItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerNoticeBinding
import java.time.LocalDate
import java.time.Period

/**
 * Adapter for the [RecyclerView] in [NoticeActivity].
 */

@RequiresApi(Build.VERSION_CODES.O)
class NoticeAdapter(
        var dataset : MutableList<NoticeItem> = mutableListOf())
    : RecyclerView.Adapter<NoticeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerNoticeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    class ViewHolder(private val binding: RecyclerNoticeBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: NoticeItem){
            with (binding){
                tvTitle.text = item.title
                tvDate.text = dateString(item.date)
            }
        }

        private fun dateString(localDate: LocalDate) : String{
            val today = LocalDate.now()
            return "${Period.between(localDate, today).days}일 전"
        }
    }

    fun removeData(pos : Int){
        dataset.removeAt(pos)
        notifyItemRemoved(pos)
    }
}