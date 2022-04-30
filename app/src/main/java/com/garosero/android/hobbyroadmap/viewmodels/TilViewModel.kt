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
    val focusDay = MutableLiveData<LocalDate>()
    val tilItems = MutableLiveData<MutableList<TilItem>>()
    init {
        initFocusDay()
        initTilItems()
    }

    // focus -day
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFocusDay(): LocalDate {
        focusDay.value?:initFocusDay()
        return focusDay.value!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initFocusDay() {
        focusDay.value = LocalDate.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setFocusDay(date: LocalDate) {
        focusDay.value = date
    }

    // til-items for recyclerview
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTilItems() : MutableList<TilItem> {
        tilItems.value?:initTilItems()
        return tilItems.value!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initTilItems() {
        val dataSet:MutableList<TilItem> = mutableListOf()
        val fDay:LocalDate = focusDay.value!!
        for (i in -3..3){
            dataSet.add(TilItem(date = fDay.plusDays(i.toLong())))
        }
        tilItems.value = dataSet
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addPreDay(){
        val size = tilItems.value!!.size
        val firstDay = getTilItems().get(0).date
        // todo : get preData
        val preData = TilItem(date = firstDay.plusDays(-1))
        tilItems.value!!.add(0, preData)
        tilItems.value!!.removeAt(size)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNextDay(){
        val size = tilItems.value!!.size
        val lastDay = getTilItems().get(size-1).date
        // todo : get nextData
        val nextData = TilItem(date = lastDay.plusDays(1))
        tilItems.value!!.add(nextData)
        tilItems.value!!.removeAt(0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateString(date: LocalDate): String {
        val pattern = "yyyy/MM/dd"
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }
}