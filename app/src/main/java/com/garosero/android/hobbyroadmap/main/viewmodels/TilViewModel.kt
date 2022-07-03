package com.garosero.android.hobbyroadmap.main.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.CourseItem
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.main.helper.CastHelper
import com.garosero.android.hobbyroadmap.main.helper.DateHelper
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilViewModel : ViewModel() {
    private val TAG = "TilViewModel"

    val focusDate : MutableLiveData<LocalDate> = MutableLiveData()
    private var tilMap : MutableMap<String, MutableList<TilItem>> = mutableMapOf()
    private var castHelper = CastHelper()

    /**
     * til item fragment 에서 수정 중인 til 데이터
     */
    private val tilItemBeingEdited : MutableLiveData<TilItem> = MutableLiveData()

    init {
        focusDate.value = LocalDate.now()
        initTilMap()
    }

    /*
    todo : til 구조를 변경
    데이터를 여러번 fetch 하지 않도록 수정해야 함.
     */
    fun getDailyData(date:LocalDate = focusDate.value!!):MutableList<TilItem>{
        return tilMap.get(DateHelper.changeDateToYYMMDD(date)) ?: mutableListOf()
    }

    fun getLocaleDateList(date: LocalDate = focusDate.value!!) : MutableList<LocalDate>{
        val list = mutableListOf<LocalDate>()
        for (i in -3..3){
            list.add(date.plusDays(i.toLong()))
        }
        return list
    }

    // til item fragment 에서 수정 중인 til 데이터
    fun getTilItemBeingEdited() : TilItem? {
        return tilItemBeingEdited.value
    }

    fun updateTilItemBeingEdited(tilItem : TilItem?){
        tilItemBeingEdited.value = tilItem
    }

    // cast response to data-model
    fun initTilMap(){
        tilMap.clear()
        val response = AppApplication.tilData

        response.values.forEach {
            val item : TilItem = castHelper.tilResopnse_to_tilItem(it)
            if (tilMap.get(item.date) == null){
                tilMap.put(item.date, mutableListOf())

            }
            tilMap.get(item.date)!!.add(item)
        }
    }
}