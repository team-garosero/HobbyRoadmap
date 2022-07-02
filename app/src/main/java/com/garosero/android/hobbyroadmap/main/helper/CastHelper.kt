package com.garosero.android.hobbyroadmap.main.helper

import android.util.Log
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.data.CourseItem
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.network.response._TilResponse

/**
 * 직접 클래스를 생성하지 말고,
 * AppApplication에 접근해서 사용해야 함
 */
class CastHelper {
    private val TAG = "CastHelper"

    fun tilItem_to_tilResponse(item: TilItem) : _TilResponse {
        val response = _TilResponse()

        with(response){
            tilId = item.tilId
            date = item.date
            uid = item.uid
            content = item.content
            courseID = "${item.categoryID} ${item.roadmapID} ${item.subContentID} ${item.courseID}"
        }

        return response
    }

    fun tilResopnse_to_tilItem(response: _TilResponse) : TilItem {
        val item = TilItem()

        try {
            item.date = response.date
            item.uid = response.uid
            item.content = response.content
            item.tilId = response.tilId

            val token = response.courseID.split(" ")

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

        return item
    }

    private fun getCourseItem(
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