package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.TilBoxItem
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerTilListBinding

/**
 * Adapter for the [RecyclerView] in [TilFragment].
 */

@RequiresApi(Build.VERSION_CODES.O)
class TilListAdapter(var dataSet: TilBoxItem) :
    RecyclerView.Adapter<TilListAdapter.ViewHolder>() {

    // submit data
    fun submitData(dataSet: TilBoxItem){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerTilListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.tilList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.tilList[position])
    }

    inner class ViewHolder(private val binding : RecyclerTilListBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: TilItem){
            binding.apply {
                // todo : item
                tvTitle.text = "course-id"
            }
        }
    }
}
