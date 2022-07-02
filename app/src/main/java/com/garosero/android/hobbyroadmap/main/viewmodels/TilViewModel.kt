package com.garosero.android.hobbyroadmap.main.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.CourseItem
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.main.helper.DateHelper
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TilViewModel : ViewModel() {
    private val TAG = "TilViewModel"

    val focusDate : MutableLiveData<LocalDate> = MutableLiveData()
    private var tilMap : MutableMap<String, MutableList<TilItem>> = mutableMapOf()

    /**
     * til item fragment 에서 수정 중인 til 데이터
     */
    private val tilItemBeingEdited : MutableLiveData<TilItem> = MutableLiveData()

    init {
        focusDate.value = LocalDate.now()
        castToTilMap()
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
    private fun castToTilMap(){
        tilMap.clear()
        val response = AppApplication.tilData

        response.values.forEach {
            val item = TilItem()

            // 문자열 분해
            try {
                val token = it.courseID.split(" ")

                with(item){
                categoryID = token[0]
                roadmapID = token[1]
                subContentID = token[2]
                courseID = token[3]

                    val courseItem = getCourseItem(categoryID, roadmapID, subContentID, courseID)
                    title = courseItem.title


                }

            } catch (e : Exception){
                Log.e(TAG, e.stackTraceToString())
            }

            item.date = it.date
            item.uid = it.uid
            item.content = it.content

            if (tilMap.get(item.date)==null){
                tilMap.put(item.date, mutableListOf())
            }
            tilMap.get(item.date)!!.add(item)
        }
    }

    fun getCourseItem(
        categoryId : String,
        roadmapId : String,
        subContentId : String,
        courseId : String
    ) : CourseItem {
        val courseResponse = AppApplication.categoryData[categoryId]?.
            roadmapMap?.get(roadmapId)?.
            subContentMap?.get(subContentId)?.
            courseMap?.get(courseId)

        val courseItem = CourseItem()
        with(courseItem) {
            xp = courseResponse?.xp ?: 0
            title = courseResponse?.title ?: ""
            order = courseResponse?.order ?: 0
            desc = courseResponse?.desc ?: ""
        }

        return courseItem
    }
}