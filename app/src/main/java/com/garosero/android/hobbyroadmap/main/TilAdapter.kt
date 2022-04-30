package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerTilBinding
import com.garosero.android.hobbyroadmap.viewmodels.TilViewModel
import java.time.LocalDate
import kotlin.math.max

/**
 * Adapter for the [RecyclerView] in [TilFragment].
 */

@RequiresApi(Build.VERSION_CODES.O)
class TilAdapter(val model:TilViewModel) :
    RecyclerView.Adapter<TilAdapter.ViewHolder>() {
    var boxSize = 0

    // submit data
    fun setLayoutWidth(width: Int){
        boxSize = width/ max(model.getTilItems().size, 1)
        notifyItemRangeChanged(0, 7)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerTilBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = model.getTilItems().size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model.getTilItems()[position])
    }

    inner class ViewHolder(private val binding : RecyclerTilBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: TilItem){
            binding.apply {

                // If there is a record, the background color changes.
                var bgColor = ContextCompat.getColor(
                    itemView.context,
                    if (item.contentList.size==0) R.color.tilBox_default
                    else R.color.tilBox_active
                )
                cvBox.setBackgroundColor(bgColor)

                // Changes the icon of items representing focus-day's records.
                if (model.getDateString(item.date).equals(
                        model.getDateString(model.getFocusDay()))){
                    ivFocus.visibility = View.VISIBLE
                } else {
                    ivFocus.visibility = View.INVISIBLE
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
}
