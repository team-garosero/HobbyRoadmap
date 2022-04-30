package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilBinding
import com.garosero.android.hobbyroadmap.viewmodels.TilViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TilFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    val model = TilViewModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTilBinding.inflate(layoutInflater)

        with (binding){
            // set recycler-view
            val adapter = TilAdapter(model)
            recycler.adapter = adapter
            //recycler.itemAnimator = null
            adapter.setOnItemClickListener(object : TilAdapter.OnItemClickListener{
                override fun onItemClick(data: TilItem, view: View) {
                    model.setFocusDay(data.date)
                }
            })

            // Since 7 boxes need to fit into the recycler-view,
            // we need a process to find the width of the recycler-view.
            recycler.viewTreeObserver.addOnGlobalLayoutListener {
                if (adapter.boxSize==0) {
                    adapter.setLayoutWidth(binding.recycler.width)
                }
            }

            // button - event
            ibnPre.setOnClickListener {
                model.addPreDay()
                adapter.notifyDataSetChanged()
            }
            ibnNext.setOnClickListener {
                model.addNextDay()
                adapter.notifyDataSetChanged()
            }

            // liveData
            val focusDayObserver = Observer<LocalDate> {
                tvDay.text = model.getDateString(it)
            }
            activity?.let { model.focusDay.observe(it, focusDayObserver) }
        }

        return binding.root
    }
}