package com.garosero.android.hobbyroadmap.main.til

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerTilListBinding

/**
 * Adapter for the [RecyclerView] in [TilParentFragment].
 */

@RequiresApi(Build.VERSION_CODES.O)
class TilListAdapter :
    RecyclerView.Adapter<TilListAdapter.ViewHolder>() {

    var dataSet = mutableListOf<TilItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerTilListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    inner class ViewHolder(private val binding : RecyclerTilListBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: TilItem){
            binding.apply {
                tvTitle.text = item.content
                layout.setOnClickListener {
                    listener?.onItemClick(item)
                }
            }
        }
    }

    // on click interface
    interface OnItemClickListener{
        fun onItemClick(item: TilItem)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}
