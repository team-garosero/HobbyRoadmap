package com.garosero.android.hobbyroadmap.main.mylist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.databinding.RecyclerMylistParentBinding
import com.garosero.android.hobbyroadmap.main.helper.SQLiteSearchHelper
import com.garosero.android.hobbyroadmap.main.viewmodels.MylistViewModel
import java.lang.Exception

/**
 * Adapter for the [RecyclerView] in [MylistFragment].
 */

class MylistParentAdapter(
    private var model: MylistViewModel)
    : RecyclerView.Adapter<MylistParentAdapter.ViewHolder>() {

    private val TAG = "MyListParentAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerMylistParentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    var dataset = mutableListOf<String>() // key List

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(dataset[position], holder)
    }

    class ViewHolder(val binding : RecyclerMylistParentBinding)
        : RecyclerView.ViewHolder(binding.root){

        val sqliteHelper = SQLiteSearchHelper(itemView.context)
    }

    fun bind(key : String, holder: ViewHolder){
        val classList: MutableList<MyClass> = model.getMyClass(key)

        holder.binding.apply {
            tvTitle.text = getTitle(holder, key)
            recycler.adapter = MylistChildAdapter(classList)
        }
    }

    private fun getTitle(holder: ViewHolder, key: String) : String {
        try {
            val keyStringList = model.parsingKey(key)
            val lClassId = keyStringList[0]
            val mClassId = keyStringList[1]
            return holder.sqliteHelper.getMClassName(lClassId, mClassId)

        } catch (e: Exception) {
            Log.e(TAG, e.stackTraceToString())
            return "error"
        }
    }

    fun submitData(dataSet : MutableList<String>){
        this.dataset = dataSet
        notifyDataSetChanged()
    }
}