package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerTilBinding
import java.time.LocalDate
import kotlin.properties.Delegates

/**
 * Adapter for the [RecyclerView] in [TilFragment].
 */

@RequiresApi(Build.VERSION_CODES.O)
class TilAdapter(
    var focusDay: LocalDate, layoutSize: Int
) :
    RecyclerView.Adapter<TilAdapter.ViewHolder>() {

    val dataset = mutableListOf<TilItem>()
    var boxSize by Delegates.notNull<Int>()

    // getData
    init {
        boxSize = layoutSize/7
        with(dataset){
            dataset.clear()
            for (i in -3..3)
                add(TilItem(date=focusDay.plusDays(i.toLong())))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerTilBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    inner class ViewHolder(private val binding : RecyclerTilBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: TilItem){
            var src = R.drawable.recycler_item_til_bg
            if (item.contentList.size>0)
                src = R.drawable.recycler_item_til_active

            binding.apply {
                Glide.with(itemView).load(src).into(ibnBox)

                val params = ibnBox.layoutParams
                params.apply {
                    width = boxSize
                    height = boxSize
                }
                ibnBox.layoutParams = params
            }
        }
    }

    // change focus day
}