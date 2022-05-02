package com.garosero.android.hobbyroadmap.viewmodels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.TilBoxItem
import com.garosero.android.hobbyroadmap.data.TilItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TilViewModel : ViewModel() {
    val focusDay = MutableLiveData<LocalDate>()
    private val tilBoxItems = MutableLiveData<MutableList<TilBoxItem>>()
    init {
        initFocusDay()
        initTilBoxItems()
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
    fun getTilBoxItems() : MutableList<TilBoxItem> {
        return tilBoxItems.value!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initTilBoxItems() {
        val dataSet:MutableList<TilBoxItem> = mutableListOf()
        val fDay:LocalDate = focusDay.value!!
        for (i in -3..3){
            dataSet.add(getDataFromDB(date = fDay.plusDays(i.toLong())))
        }
        tilBoxItems.value = dataSet
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addPreDay(){
        val size = getTilBoxItems().size
        val dataSet = getTilBoxItems()
        val firstDay = dataSet.get(0).date
        val preData = getDataFromDB(firstDay.plusDays(-1))
        tilBoxItems.value!!.add(0, preData)
        tilBoxItems.value!!.removeAt(size)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNextDay(){
        val size = getTilBoxItems().size
        val dataSet = getTilBoxItems()
        val lastDay = dataSet.get(size-1).date
        val nextData = getDataFromDB(lastDay.plusDays(1))
        tilBoxItems.value!!.add(nextData)
        tilBoxItems.value!!.removeAt(0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateString(date: LocalDate): String {
        val pattern = "yyyy/MM/dd"
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }

    // todo : get nextData
    private fun getDataFromDB(date: LocalDate): TilBoxItem {
        val random = (0..5).random()
        val list = mutableListOf<TilItem>()
        for (i in 0 until random){
            list.add(TilItem(date=date))
        }
        val data = TilBoxItem(date, list)
        return data
    }

    fun getFocusTilBoxItem(): TilBoxItem{
        return getDataFromDB(date = focusDay.value!!)
    }

    // freq - group
    fun getBgColor(context: Context, item: TilBoxItem): Int {
        var src:Int
        when (item.tilList.size){
            0 -> src = R.color.tilBox_g0
            1 -> src = R.color.tilBox_g1
            2 -> src = R.color.tilBox_g2
            3 -> src = R.color.tilBox_g3
            else -> src = R.color.tilBox_g4
        }
        return ContextCompat.getColor(context, src)
    }
}