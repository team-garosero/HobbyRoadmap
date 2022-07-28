package com.garosero.android.hobbyroadmap.main.mylist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.databinding.RecyclerMylistChildBinding
import com.garosero.android.hobbyroadmap.databinding.RecyclerMylistTitleBinding
import com.garosero.android.hobbyroadmap.main.helper.SQLiteSearchHelper
import com.garosero.android.hobbyroadmap.syllabus.SyllabusActivity

/**
 * 2022.07.29
 * 기존에는 recyclerview 를 두번 감싸서 title과 content 부분을 표현.
 * view type에 따라 view bind를 다르게 처리하도록 하여, recyclerview를 한번만 사용하도록 변경하였음.
 */
class MylistAdapter(
    private var sqLiteSearchHelper : SQLiteSearchHelper
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ItemViewType.Content.value) {
            val view = RecyclerMylistChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ContentViewHolder(view)
        }
        else {
            val view = RecyclerMylistTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TitleViewHolder(view)
        } // end if
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ContentViewHolder)?.bind(dataset[position] as MyClass)
        (holder as? TitleViewHolder)?.bind(dataset[position] as String)
    }

    override fun getItemCount(): Int = dataset.size

    // ================================ view holder
    inner class ContentViewHolder(val binding: RecyclerMylistChildBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : MyClass){
            binding.apply {
                tvTitle.text = item.subClassName
                tvDate.text = item.lastAccess
                tvPercentage.text = "${getPercentage(item)}%"
                ivRoadmap.setImageResource(getImageSrc(item.LClassId))

                layout.setOnClickListener {
                    goSyllabusActivity(
                        mContext = itemView.context,
                        classPathList = ArrayList(item.classPath.split(" ")))
                }
            }
        }
    }

    inner class TitleViewHolder(val binding: RecyclerMylistTitleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(title : String){
            binding.tvTitle.text = title
        }
    }

    // ================================ data type
    override fun getItemViewType(position: Int): Int {
        if (dataset[position] is MyClass) return ItemViewType.Content.value
        return ItemViewType.Title.value
    }

    private enum class ItemViewType(val value : Int){
        Title(100), Content(110)
    }

    // ================================ data control
    var dataset = ArrayList<Any>()

    fun submitData(clazz: ArrayList<MyClass>){
        (clazz as? ArrayList<Any>)?.let { scanData(it) }
        notifyDataSetChanged()
    }

    fun scanData(dataset : ArrayList<Any>){
        val newDataSet = ArrayList<Any>()
        var preLClass: String? = null
        var preMClass: String? = null

        for (item : Any in dataset){
            if (item !is MyClass){
                continue
            } // end if

            if (!(item.LClassId == preLClass && item.MClassId == preMClass)) {
                newDataSet.add(item.MClassName) // add title

                preLClass = item.LClassId
                preMClass = item.MClassId

            } // end if

            newDataSet.add(item) // add content
        } // end for

        this.dataset = newDataSet
    }

    // ==================================== click event
    private fun goSyllabusActivity(mContext : Context, classPathList : ArrayList<String>){
        val intent = Intent(mContext, SyllabusActivity::class.java)
        intent.putExtra("classCd", classPathList)
        mContext.startActivity(intent)
    }

    // ===================================== get item resource
    private fun getPercentage(item: MyClass): Int {
        if (sqLiteSearchHelper == null) return 0

        val subClassSize = sqLiteSearchHelper!!.getSubClassSize(
            item.LClassId,
            item.MClassId,
            item.MClassId,
            item.subClassId
        )

        if (subClassSize == 0) {
            // todo throw error
            return 0
        }

        return (item.modules.size * 100) / subClassSize!!
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