package com.garosero.android.hobbyroadmap.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.data.TilItem
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilViewModel : ViewModel() {

    val liveFocusDate = MutableLiveData<LocalDate>()
    init {
        liveFocusDate.value = LocalDate.now()
    }
    fun getFocusDate() = liveFocusDate.value!!

    // todo : getData
    fun getDailyData(date:LocalDate):MutableList<TilItem>{
        var count = (date.dayOfYear).mod(6)
        val list = mutableListOf<TilItem>()
        for (i in 0 until count){
            list.add(TilItem(date = date, content = "content ${i}"))
        }
        return list
    }
}