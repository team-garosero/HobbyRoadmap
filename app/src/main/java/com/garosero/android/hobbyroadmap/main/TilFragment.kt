package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.garosero.android.hobbyroadmap.data.TilBoxItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilBinding
import com.garosero.android.hobbyroadmap.viewmodels.TilViewModel
import java.time.LocalDate

class TilFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    val model = TilViewModel()
    lateinit var boxAdapter: TilBoxAdapter
    lateinit var listAdapter: TilListAdapter
    lateinit var binding: FragmentTilBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTilBinding.inflate(layoutInflater)

        setTilBox()
        setTilList()

        // liveData
        val focusDayObserver = Observer<LocalDate> {
            binding.tvDay.text = model.getDateString(it)
        }
        activity?.let { model.focusDay.observe(it, focusDayObserver) }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTilBox(){
        with (binding){
            // recycler-view : Til
            boxAdapter = TilBoxAdapter(model)
            recyclerBox.adapter = boxAdapter
            recyclerBox.itemAnimator = null

            // Since 7 boxes need to fit into the recycler-view,
            // we need a process to find the width of the recycler-view.
            recyclerBox.viewTreeObserver.addOnGlobalLayoutListener {
                if (boxAdapter.boxSize==0) {
                    boxAdapter.setLayoutWidth(binding.recyclerBox.width)
                }
            }

            // box adapter onClick
            boxAdapter.setOnItemClickListener(object :TilBoxAdapter.OnItemClickListener{
                override fun onItemClick(data: TilBoxItem, view: View) {
                    model.setFocusDay(data.date)
                    boxAdapter.notifyDataSetChanged()
                    listAdapter.submitData(data)
                }
            })

            // button - event
            ibnPre.setOnClickListener {
                model.addPreDay()
                boxAdapter.notifyDataSetChanged()
            }
            ibnNext.setOnClickListener {
                model.addNextDay()
                boxAdapter.notifyDataSetChanged()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTilList(){
        with(binding){
            val item = model.getFocusTilBoxItem()
            listAdapter = TilListAdapter(item)
            recyclerList.adapter = listAdapter
        }
    }
}