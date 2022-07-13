package com.garosero.android.hobbyroadmap.main.searchVer2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.databinding.RecyclerSearchListBinding

class SearchListVer2Adapter
    : RecyclerView.Adapter<SearchListVer2Adapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListVer2Adapter.ViewHolder {
        val view = RecyclerSearchListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    var dataset : MutableList<ClassPath> = mutableListOf()
    fun submitData(dataset : MutableList<ClassPath>){
        this.dataset = dataset
        notifyDataSetChanged()
    }

    var searchType = SearchType.lClass
    inner class ViewHolder(private val binding: RecyclerSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(classPath: ClassPath){
            binding.llClass.setOnClickListener {
                onClickCallback?.onClick(classPath)
            }

            binding.tvPath.text =  classPath.getLastName(searchType)
        }
    }

    interface OnClickCallback{
        fun onClick(classPath: ClassPath)
    }
    var onClickCallback : OnClickCallback? = null

}