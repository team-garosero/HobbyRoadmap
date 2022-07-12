package com.garosero.android.hobbyroadmap.main.mylist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.databinding.RecyclerMylistChildBinding
import com.garosero.android.hobbyroadmap.main.helper.SQLiteSearchHelper
import com.garosero.android.hobbyroadmap.syllabus.SyllabusActivity

/**
 * Adapter for the [RecyclerView] in [MylistParentAdapter].
 */

class MylistChildAdapter(var dataset : MutableList<MyClass>)
    : RecyclerView.Adapter<MylistChildAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerMylistChildBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    inner class ViewHolder(private val binding : RecyclerMylistChildBinding)
        : RecyclerView.ViewHolder(binding.root){
        private val sQLiteHelper = SQLiteSearchHelper(itemView.context)

        fun bind(item : MyClass){
            binding.apply {
                tvTitle.text = item.subClassName
                tvDate.text = item.lastAccess
                tvPercentage.text = "${getPercentage(item, sQLiteHelper)}%"
                layout.setOnClickListener {
                    goSyllabusActivity(
                        mContext = itemView.context,
                        classPathList = ArrayList(item.classPath.split(" ")))
                }

                ivRoadmap.setImageResource(getImageSrc(item.LClassId))
            }
        }
    }

    private fun goSyllabusActivity(mContext : Context, classPathList : ArrayList<String>){
        val intent = Intent(mContext, SyllabusActivity::class.java)
        intent.putExtra("classCd", classPathList)
        mContext.startActivity(intent)
    }


    // ===================================== get item resource =====================================
    private fun getPercentage(item: MyClass, sqLiteSearchHelper: SQLiteSearchHelper): Int{
        val subClassSize = sqLiteSearchHelper.getSubClassSize(item.LClassId, item.MClassId, item.MClassId, item.subClassId)

        if (subClassSize == 0){
            // todo throw error
            return 0
        }

        val percentage = (item.modules.size * 100) / subClassSize
        return percentage
    }

    private fun getImageSrc(lClassID : String) : Int{
        return when (lClassID){
            "01" -> R.drawable.ic_01
            "02" -> R.drawable.ic_02
            "03" -> R.drawable.ic_03
            "04" -> R.drawable.ic_04
            "05" -> R.drawable.ic_05
            "06" -> R.drawable.ic_06
            "07" -> R.drawable.ic_07
            "08" -> R.drawable.ic_08
            "09" -> R.drawable.ic_09

            "10" -> R.drawable.ic_10
            "11" -> R.drawable.ic_11
            "12" -> R.drawable.ic_12
            "13" -> R.drawable.ic_13
            "14" -> R.drawable.ic_14
            "15" -> R.drawable.ic_15
            "16" -> R.drawable.ic_16
            "17" -> R.drawable.ic_17
            "18" -> R.drawable.ic_18
            "19" -> R.drawable.ic_19

            "20" -> R.drawable.ic_20
            "21" -> R.drawable.ic_21
            "22" -> R.drawable.ic_22
            "23" -> R.drawable.ic_23
            "24" -> R.drawable.ic_24

            else -> R.color.tilBox_g0
        }
    }
}