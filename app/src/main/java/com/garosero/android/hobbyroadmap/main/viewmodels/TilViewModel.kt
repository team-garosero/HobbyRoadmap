package com.garosero.android.hobbyroadmap.main.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.main.helper.CastHelper
import com.garosero.android.hobbyroadmap.network.NetworkFactory.Companion.request
import com.garosero.android.hobbyroadmap.network.request.ReadTilRequest
import com.garosero.android.hobbyroadmap.network.request.RequestListener
import com.garosero.android.hobbyroadmap.network.response.TilResponse
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TilViewModel : ViewModel() {
    val TAG = "TilViewModel"

    val focusDate : MutableLiveData<LocalDate> = MutableLiveData<LocalDate>()
    var tilMap : MutableLiveData<MutableMap<String, MutableList<TilItem>>> = MutableLiveData()

    var tilXp = 100 // til 하나당 증가하는 경험치

    init {
        focusDate.value = LocalDate.now()

        // get til data
        request(ReadTilRequest(), object : RequestListener(){
            override fun onRequestSuccess(data: Any) {
                val tilData = data as MutableMap<String, TilResponse>
                castToTilMap(tilData)
            }
        })
    }

    fun getDailyData(date:LocalDate = focusDate.value!!):MutableList<TilItem>{
        return tilMap.value?.get(dateStringYMD(date)) ?: mutableListOf()
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

    fun isToday(date: LocalDate = focusDate.value!!) : Boolean {
        return date.isEqual(LocalDate.now())
    }

    // cast response to data-model
    private fun castToTilMap(response : MutableMap<String, TilResponse>){
        val _tilMap = mutableMapOf<String, MutableList<TilItem>>()

        response.values.forEach {
            if (it.uid == FirebaseAuth.getInstance().uid) {

                val item = CastHelper.tilresponseToTilitem(it)

                if (_tilMap[item.date] == null) {
                    _tilMap[item.date] = mutableListOf()
                }
                _tilMap[item.date]!!.add(item)
            } // end if
        }

        if (tilMap.value == null || tilMap.value != _tilMap) {
            tilMap.value = _tilMap
        } // end if
    }
}