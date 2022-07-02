package com.garosero.android.hobbyroadmap.main.til

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.garosero.android.hobbyroadmap.databinding.FragmentTilParentBinding
import com.garosero.android.hobbyroadmap.main.helper.DateHelper
import com.garosero.android.hobbyroadmap.main.viewmodels.TilViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilParentFragment : Fragment() {
    private var weeklyAdapter: TilBoxAdapter? = null
    lateinit var binding: FragmentTilParentBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTilParentBinding.inflate(layoutInflater)

        initLiveData()
        initRecyclerview()
        initView()

        return binding.root
    }

    // date picker
    private fun showDatePickerDialog(){
        val focusDate = model.focusDate.value!!
        val dateSetListener = DatePickerDialog.OnDateSetListener {
                _, year, month, dayOfMonth ->
            model.focusDate.value = LocalDate.of(year, month+1, dayOfMonth)
        }
        context?.let {
            DatePickerDialog(
                it, dateSetListener,
                focusDate.year, focusDate.month.value, focusDate.dayOfMonth).show()
        }
    }

    private fun initRecyclerview(){
        // recycler view
        with (binding){
            weeklyAdapter = TilBoxAdapter(model)
            recyclerBox.adapter = weeklyAdapter
            recyclerBox.itemAnimator = null

            // click listener
            weeklyAdapter?.setOnItemClickListener(object : TilBoxAdapter.OnItemClickListener{
                override fun onItemClick(localDate: LocalDate) {
                    model.focusDate.value = localDate
                }
            })
        }
    }

    private fun initView(){
        binding.btnDay.setOnClickListener { showDatePickerDialog() }
        binding.btnToday.setOnClickListener {
            model.focusDate.value = LocalDate.now()
        }
    }

    private fun initLiveData(){
        val observer = Observer<LocalDate>{
            binding.btnDay.text = DateHelper.changeDateToYYMMDD()
            if (weeklyAdapter != null){
                if (!weeklyAdapter!!.dataSet.contains(it))
                    weeklyAdapter?.dataSet = model.getLocaleDateList()
            }
            weeklyAdapter?.notifyDataSetChanged()
        }
        model.focusDate.observe(viewLifecycleOwner, observer)
    }

    // shared data
    companion object {
        val model = TilViewModel()
    }
}