package com.garosero.android.hobbyroadmap.main.til

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.databinding.FragmentTilParentBinding
import com.garosero.android.hobbyroadmap.main.viewmodels.TilViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilParentFragment : Fragment() {
    private var weeklyAdapter: TiBoxAdapter? = null
    lateinit var binding: FragmentTilParentBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTilParentBinding.inflate(layoutInflater)

        initRecyclerView()
        initView()
        addObserver()

        return binding.root
    }

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    private fun addObserver(){
        // live data
        val observer = Observer<LocalDate>{
            weeklyAdapter?.notifyDataSetChanged()
            binding.btnDay.text = "${model.dateStringYMD()} ▼"

            with(binding.btnToday){
                binding.btnDay.text = model.dateStringYMD()
                if (weeklyAdapter != null){
                    if (!weeklyAdapter!!.dataSet.contains(it))
                        weeklyAdapter?.dataSet = model.getLocaleDateList()
                }

                if (model.isToday(date = it)){
                    // 오늘인 경우
                    setBackgroundResource(R.drawable.til_act_button_filled)
                    setTextColor(Color.WHITE)

                } else {
                    setBackgroundResource(R.drawable.til_act_button_border)
                    setTextColor(Color.BLUE)

                } // end if
            }

        }
        model.focusDate.observe(viewLifecycleOwner, observer)

        val tilObserver = Observer<MutableMap<String, MutableList<TilItem>>> {
            weeklyAdapter?.notifyDataSetChanged()
        }
        model.tilMap.observe(viewLifecycleOwner, tilObserver)
    }

    private fun initView(){
        // on click
        binding.btnDay.setOnClickListener { showDatePickerDialog() }
        binding.btnToday.setOnClickListener {
            model.focusDate.value = LocalDate.now()
        }

        changeFragment(TilListFragment())
    }

    private fun initRecyclerView(){
        // recycler view
        with (binding){
            weeklyAdapter = TiBoxAdapter(model)
            recyclerBox.adapter = weeklyAdapter
            recyclerBox.itemAnimator = null

            // click listener
            weeklyAdapter?.setOnItemClickListener(object : TiBoxAdapter.OnItemClickListener{
                override fun onItemClick(localDate: LocalDate) {
                    model.focusDate.value = localDate
                }
            })
        }
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
                focusDate.year, focusDate.month.value-1, focusDate.dayOfMonth).show()
        }
    }

    fun changeFragment(fragment: Fragment){
        childFragmentManager.beginTransaction().replace(R.id.nav_host_til, fragment).commit()
    }

    // shared data
    companion object {
        val model = TilViewModel()
    }
}