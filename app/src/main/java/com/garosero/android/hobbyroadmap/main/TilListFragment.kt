package com.garosero.android.hobbyroadmap.main

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.garosero.android.hobbyroadmap.databinding.FragmentTilListBinding
import com.garosero.android.hobbyroadmap.myutil.DateHelper
import com.garosero.android.hobbyroadmap.viewmodels.TilViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilListFragment : Fragment() {
    var boxAdapter: TilBoxAdapter? = null
    var listAdapter: TilListAdapter? = null

    lateinit var binding: FragmentTilListBinding
    private val model = TilViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTilListBinding.inflate(layoutInflater)

        // live data
        with(model){
            val observer = Observer<LocalDate>{
                boxAdapter?.submitFocusDate(it)
                listAdapter?.submitData(model.getDailyData(it))
                setText(it)
            }
            activity?.let { liveFocusDate.observe(it, observer) }
        }

        // init view
        setTilBox()
        setTilList()
        setText(model.getFocusDate())

        // on click
        binding.btnDay.setOnClickListener { showDatePickerDialog() }
        binding.btnToday.setOnClickListener {
            model.liveFocusDate.value = LocalDate.now()
            boxAdapter?.submitDateList(getLocaleDateList(model.getFocusDate()))
        }

        return binding.root
    }

    private fun setTilBox(){
        with (binding){
            val list = getLocaleDateList(model.getFocusDate())
            boxAdapter = TilBoxAdapter(list, model.getFocusDate())
            recyclerBox.adapter = boxAdapter
            recyclerBox.itemAnimator = null

            // click listener
            boxAdapter?.setOnItemClickListener(object : TilBoxAdapter.OnItemClickListener{
                override fun onItemClick(date: LocalDate) {
                    model.liveFocusDate.value = date
                }
            })
        }
    }

    private fun setTilList(){
        listAdapter = TilListAdapter(model.getDailyData(model.getFocusDate()))
        binding.recyclerList.adapter = listAdapter
    }

    private fun setText(date: LocalDate){
        val helper = DateHelper()
        binding.btnDay.text = helper.dateStringYMD(date)
    }

    private fun getLocaleDateList(date: LocalDate) : MutableList<LocalDate>{
        val list = mutableListOf<LocalDate>()
        for (i in -3..3){
            list.add(date.plusDays(i.toLong()))
        }
        return list
    }

    private fun showDatePickerDialog(){
        val date = model.getFocusDate()
        val dateSetListener = DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth ->
            model.liveFocusDate.value = LocalDate.of(year, month+1, dayOfMonth)
            boxAdapter?.submitDateList(getLocaleDateList(model.getFocusDate()))
        }
        context?.let {
            DatePickerDialog(
                it, dateSetListener, date.year, date.month.value, date.dayOfMonth).show()
        }
    }
}