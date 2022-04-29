package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerTilBinding
import java.time.LocalDate

/**
 * Adapter for the [RecyclerView] in [TilFragment].
 */

@RequiresApi(Build.VERSION_CODES.O)
class TilAdapter(
    var layoutSize: Int = 0,
    var focusDay: LocalDate = LocalDate.now()
    ) :
    RecyclerView.Adapter<TilAdapter.ViewHolder>() {

    var dataSet:MutableList<TilItem> = mutableListOf()
    var boxSize = layoutSize/7
    init {
        for (i in -3..3){
            dataSet.add(TilItem(date = focusDay.plusDays(i.toLong())))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerTilBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    inner class ViewHolder(private val binding : RecyclerTilBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: TilItem){
            binding.apply {

                // If there is a record, the background color changes.
                var bgColor = ContextCompat.getColor(
                    itemView.context,
                    getBgColor(item.contentList.size)
                )
                ivFocus.setBackgroundColor(bgColor)

                // Changes the icon of items representing focus-day's records.
                try {
                    var ic_src: Int
                    if (isEqual(item.date, focusDay)) ic_src = R.drawable.ic_baseline_edit_24
                    else ic_src = R.drawable.empty_box
                    Glide.with(itemView)?.load(ic_src)?.into(ivFocus)
                } catch (e:Exception){

                }

                // 7 boxes must be displayed on one screen,
                // so the width and height must be adjusted.
                var params = layout.layoutParams
                params.apply {
                    width = boxSize
                    height = boxSize
                }
                layout.layoutParams = params

                // onclick - listener
                cvBox.setOnClickListener{
                    listener?.onItemClick(item, it)
                    notifyDataSetChanged()
                }
            }
        }
    }

    // on click interface
    interface OnItemClickListener{
        fun onItemClick(data: TilItem, view: View)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    // get color
    private fun getBgColor(dataCount:Int):Int{
        if (dataCount==0) return R.color.tilBox_default
        else return R.color.tilBox_active
    }

    private fun isEqual(d1:LocalDate, d2: LocalDate): Boolean{
        return (d1.year==d2.year) && (d1.month==d2.month) && (d1.dayOfMonth==d2.dayOfMonth)
    }

    // change focus week
    fun addPreDay(){
        val tilItem = dataSet[0]
        dataSet.add(0, TilItem(date = tilItem.date.plusDays(-1)))
        dataSet.removeAt(6)
        notifyDataSetChanged()
    }

    fun addNextDay(){
        val tilItem = dataSet[6]
        dataSet.add(TilItem(date = tilItem.date.plusDays(1)))
        dataSet.removeAt(0)
        notifyDataSetChanged()
    }
}
