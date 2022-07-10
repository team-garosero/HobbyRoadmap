package com.garosero.android.hobbyroadmap.main.mylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.databinding.RecyclerMylistParentBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.MylistViewModel

/**
 * Adapter for the [RecyclerView] in [MylistFragment].
 */

class MylistParentAdapter(
    var model: MylistViewModel)
    : RecyclerView.Adapter<MylistParentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclerMylistParentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(view)
    }

    var dataset = mutableListOf<String>()
    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mClassID = dataset[position]
        holder.bind(model.getMClassName(mClassID), model.getMyClass(mClassID))
    }

    class ViewHolder(private val binding : RecyclerMylistParentBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(title : String, classList: MutableList<MyClass>){
            binding.apply {
                tvTitle.text = title
                recycler.adapter = MylistChildAdapter(classList)
            }
        }
    }

    fun submitData(dataSet : MutableList<String>){
        this.dataset = dataSet
        notifyDataSetChanged()
    }
}