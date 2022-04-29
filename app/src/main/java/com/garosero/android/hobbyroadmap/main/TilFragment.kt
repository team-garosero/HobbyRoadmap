package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TilFragment : Fragment() {
    lateinit var adapter:TilAdapter
    lateinit var binding:FragmentTilBinding
    var recyclerWidth:Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTilBinding.inflate(inflater, container, false)

        /* Since 7 boxes need to fit into the recycler-view,
        we need a process to find the width of the recycler-view. */
        binding.recycler.viewTreeObserver.addOnGlobalLayoutListener {
            if (recyclerWidth==0) {
                recyclerWidth = binding.recycler.width
                initView()
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initView(){
        // set focus day
        binding.tvDay.text = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

        // recycler view adapter
        adapter = TilAdapter(layoutSize = recyclerWidth)
        binding.recycler.adapter = adapter
        adapter.setOnItemClickListener(object : TilAdapter.OnItemClickListener{
            override fun onItemClick(data: TilItem, view: View) {
                binding.tvDay.text = data.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                adapter.focusDay = data.date // change focusDay
            }
        })

        // button onclick
        binding.ibnPre.setOnClickListener{adapter.addPreDay()}
        binding.ibnNext.setOnClickListener{adapter.addNextDay()}
    }
}