package com.garosero.android.hobbyroadmap.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
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

    /*
    todo : til 구조를 변경
    데이터를 여러번 fetch 하지 않도록 수정해야 함.
     */
    fun getDailyData(date:LocalDate = focusDate.value!!):MutableList<TilItem>{
        val data = mutableListOf<TilItem>()
        AppApplication.tilArraylist.forEach {
            if(it.date.equals(dateStringYMD(date))) {
                data.add(it)
            }
        }

        return data
    }

    fun getLocaleDateList(date: LocalDate = focusDate.value!!) : MutableList<LocalDate>{
        val list = mutableListOf<LocalDate>()
        for (i in -3..3){
            list.add(date.plusDays(i.toLong()))
        }
        return list
    }

    fun dateStringYMD(date: LocalDate = focusDate.value!!): String{
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}