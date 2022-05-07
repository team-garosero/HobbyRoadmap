package com.garosero.android.hobbyroadmap.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.garosero.android.hobbyroadmap.databinding.FragmentTilBinding
import com.garosero.android.hobbyroadmap.myutil.DateHelper
import com.garosero.android.hobbyroadmap.viewmodels.TilViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilFragment : Fragment() {
    lateinit var boxAdapter: TilBoxAdapter
    lateinit var listAdapter: TilListAdapter

    lateinit var binding: FragmentTilBinding
    private val model = TilViewModel()
    private val today = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTilBinding.inflate(layoutInflater)

        setTilBox()
        setTilList()
        setText(today)
        return binding.root
    }

    private fun setTilBox(){
        with (binding){
            val list = getLocaleDateList()
            boxAdapter = TilBoxAdapter(list, today)
            recyclerBox.adapter = boxAdapter
            recyclerBox.itemAnimator = null

            // click listener
            boxAdapter.setOnItemClickListener(object : TilBoxAdapter.OnItemClickListener{
                override fun onItemClick(date: LocalDate) {
                    boxAdapter.submitFocusDate(date)
                    listAdapter.submitData(model.getDailyData(boxAdapter.focusDate))
                    setText(date)
                }
            })
        }
    }

    private fun setTilList(){
        listAdapter = TilListAdapter(model.getDailyData(today))
        binding.recyclerList.adapter = listAdapter
    }

    private fun setText(date: LocalDate){
        val helper = DateHelper()
        binding.tvDay.text = helper.dateStringYMD(date)
    }

    private fun getLocaleDateList() : MutableList<LocalDate>{
        val list = mutableListOf<LocalDate>()
        for (i in -3..3){
            list.add(today.plusDays(i.toLong()))
        }
        return list
    }
}