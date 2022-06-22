package com.garosero.android.hobbyroadmap.main.viewmodels

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

    val focusDate : MutableLiveData<LocalDate>
    var tilMap : MutableMap<String, MutableList<TilItem>>

    init {
        focusDate = MutableLiveData<LocalDate>()
        tilMap = mutableMapOf()

        focusDate.value = LocalDate.now()
        castToTilMap()
    }

    /*
    todo : til 구조를 변경
    데이터를 여러번 fetch 하지 않도록 수정해야 함.
     */
    fun getDailyData(date:LocalDate = focusDate.value!!):MutableList<TilItem>{
        return tilMap.get(dateStringYMD(date)) ?: mutableListOf()
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

    // cast response to data-model
    private fun castToTilMap(){
        tilMap.clear()
        val response = AppApplication.tilData

        response.values.forEach {
            val item = TilItem()
            item.courseID = it.courseID
            item.date = it.date
            item.uid = it.uid
            item.content = it.content

            if (tilMap.get(item.date)==null){
                tilMap.put(item.date, mutableListOf())
            }
            tilMap.get(item.date)!!.add(item)
        }
    }
}