package com.garosero.android.hobbyroadmap.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.data.TilItem
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilViewModel : ViewModel() {

    // todo : getData
    fun getDailyData(date:LocalDate):MutableList<TilItem>{
        var count = (date.dayOfYear).times(2).mod(6)
        val list = mutableListOf<TilItem>()
        for (i in 0..count){
            list.add(TilItem(date = date, content = "content ${i}"))
        }
        return list
    }
}