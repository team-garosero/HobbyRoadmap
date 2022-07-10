package com.garosero.android.hobbyroadmap.main.helper

import android.util.Log
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.network.response.TilResponse
import java.lang.Exception

class CastHelper {
    companion object{
        val TAG = "CastHelper"

        fun tilresponseToTilitem(tilResponse: TilResponse) : TilItem{
            val tilItem = TilItem()

            with(tilItem){
                content = tilResponse.content
                date = tilResponse.date
                moduleName = tilResponse.moduleName
                moduleDesc = tilResponse.moduleDesc
                modulePath = tilResponse.modulePath
                tilId = tilResponse.tilId

                try {
                    val path = tilResponse.modulePath.split(" ")
                    LClassId = path[0]
                    MClassId = path[1]
                    SClassId = path[2]
                    subClassId = path[3]

                } catch (e : Exception){
                    Log.e(TAG, e.stackTraceToString())
                }
            }

            return tilItem
        }
    }
}