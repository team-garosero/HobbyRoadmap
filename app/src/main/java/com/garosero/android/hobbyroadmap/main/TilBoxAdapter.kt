package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.TilBoxItem
import com.garosero.android.hobbyroadmap.databinding.RecyclerTilBoxBinding
import com.garosero.android.hobbyroadmap.viewmodels.TilViewModel
import kotlin.math.max

/**
 * Adapter for the [RecyclerView] in [TilFragment].
 */

@RequiresApi(Build.VERSION_CODES.O)
class TilBoxAdapter(val model:TilViewModel) :
    RecyclerView.Adapter<TilBoxAdapter.ViewHolder>() {
    var boxSize = 0

    // submit data
    fun setLayoutWidth(width: Int){
        boxSize = width/ max(model.getTilBoxItems().size, 1)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerTilBoxBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = model.getTilBoxItems().size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model.getTilBoxItems()[position])
    }

    inner class ViewHolder(private val binding : RecyclerTilBoxBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: TilBoxItem){
            binding.apply {

                // If there is a record, the background color changes.
                cvBox.setBackgroundColor(model.getBgColor(itemView.context, item))

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
                }
            }
        }
    }

    // on click interface
    interface OnItemClickListener{
        fun onItemClick(data: TilBoxItem, view: View)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}
