package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.RecyclerTilBoxBinding
import com.garosero.android.hobbyroadmap.myutil.DateHelper
import com.garosero.android.hobbyroadmap.viewmodels.TilViewModel
import java.time.LocalDate

/**
 * Adapter for the [RecyclerView] in [TilFragment].
 */

@RequiresApi(Build.VERSION_CODES.O)
class TilBoxAdapter(
    var dataSet: MutableList<LocalDate>,
    var focusDate: LocalDate) :
    RecyclerView.Adapter<TilBoxAdapter.ViewHolder>() {

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
                cvBox.setBackgroundColor(ContextCompat.getColor(itemView.context, src))

                // Changes the icon of items representing focus-day's records.
                if (date.equals(focusDate)){
                    ivFocus.visibility = View.VISIBLE
                } else {
                    ivFocus.visibility = View.INVISIBLE
                }

                // onclick - listener
                cvBox.setOnClickListener{
                    listener?.onItemClick(date)
                }

                // todo : testing text
                tvTest.text = DateHelper().dateStringMD(date)
            }
        }
    }

    // on click interface
    interface OnItemClickListener{
        fun onItemClick(data: LocalDate)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    // submit data
    fun submitFocusDate(date : LocalDate){
        focusDate = date
        notifyDataSetChanged()
    }

    // bgColor
    private val model = TilViewModel()
    fun getBgColorSrc(date: LocalDate) : Int{
        val count = model.getDailyData(date).size
        when (count) {
            0 -> return R.color.tilBox_g0
            1 -> return R.color.tilBox_g1
            2 -> return R.color.tilBox_g2
            3 -> return R.color.tilBox_g3
            else -> return R.color.tilBox_g4
        }
    }
}
