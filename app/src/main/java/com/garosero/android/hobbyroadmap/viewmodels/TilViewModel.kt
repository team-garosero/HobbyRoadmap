package com.garosero.android.hobbyroadmap.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.data.TilItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TilViewModel : ViewModel() {
    // shared data
    var focusDate = MutableLiveData<LocalDate>()
    init {
        focusDate.value = LocalDate.now()
    }

    // todo : getData
    fun getDailyData(date:LocalDate = focusDate.value!!):MutableList<TilItem>{
        val count = (date.dayOfYear).mod(5)
        val list = mutableListOf<TilItem>()
        for (i in 0 until count){
            //list.add(TilItem(date = date, content = "content $i"))
        }
        return list
    }

    fun getLocaleDateList(date: LocalDate = focusDate.value!!) : MutableList<LocalDate>{
        val list = mutableListOf<LocalDate>()
        for (i in -3..3){
            list.add(date.plusDays(i.toLong()))
        }
        return list
    }

    fun dateStringYMD(date: LocalDate = focusDate.value!!): String{
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    }
}