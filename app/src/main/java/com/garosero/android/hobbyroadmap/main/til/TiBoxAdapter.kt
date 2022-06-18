package com.garosero.android.hobbyroadmap.main.til

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.RecyclerTilBoxBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.TilViewModel
import java.time.LocalDate

/**
 * Adapter for the [RecyclerView] in [TilParentFragment].
 */

@RequiresApi(Build.VERSION_CODES.O)
class TiBoxAdapter(val model : TilViewModel) :
    RecyclerView.Adapter<TiBoxAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerTilBoxBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    inner class ViewHolder(private val binding : RecyclerTilBoxBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(date: LocalDate){
            binding.apply {
                // If there is a record, the background color changes.
                val src = getBgColorSrc(date)
                layout.background.setTint(ContextCompat.getColor(itemView.context, src))

                // Changes the icon of items representing focus-day's records.
                if (date == focusDate()){
                    ivFocus.visibility = View.VISIBLE
                } else {
                    ivFocus.visibility = View.INVISIBLE
                }

                // onclick - listener
                layout.setOnClickListener{
                    listener?.onItemClick(date)
                }
            }
        }
    }

    // on click interface
    interface OnItemClickListener{
        fun onItemClick(localDate: LocalDate)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    // control data
    var dataSet = model.getLocaleDateList()
    private fun focusDate() = model.focusDate.value!!

    // bgColor
    fun getBgColorSrc(date: LocalDate) : Int{
        return when (model.getDailyData(date).size) {
            0 -> R.color.tilBox_g0
            1 -> R.color.tilBox_g1
            2 -> R.color.tilBox_g2
            3 -> R.color.tilBox_g3
            else -> R.color.tilBox_g4
        }
    }
}
