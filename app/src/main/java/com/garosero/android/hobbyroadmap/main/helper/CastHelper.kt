package com.garosero.android.hobbyroadmap.main.helper

import android.content.Context
import android.util.Log
import com.garosero.android.hobbyroadmap.data.MyClass
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.network.response.MyClassResponse
import com.garosero.android.hobbyroadmap.network.response.TilResponse
import java.lang.Exception

class CastHelper {
    companion object{
        val TAG = "CastHelper"

        //======================================= TIL ==============================================
        fun tilresponseToTilitem(tilResponse: TilResponse) : TilItem{
            val tilItem = TilItem()

            with(tilItem){
                content = tilResponse.content
                date = tilResponse.date
                moduleName = tilResponse.moduleName
                moduleDesc = tilResponse.moduleDesc
                modulePath = tilResponse.modulePath
                uid = tilResponse.uid
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

        fun tilitemToTilresponse(tilItem: TilItem) : TilResponse{
            val response = TilResponse()

            with(response){
                uid = tilItem.uid
                content = tilItem.content
                date = tilItem.date

                moduleName = tilItem.moduleName
                moduleDesc = tilItem.moduleDesc
                modulePath = tilItem.modulePath

                tilId = tilItem.tilId
            }

            return response
        }

        //======================================= MyClass ==========================================
        private var sqliteSearchHelper : SQLiteSearchHelper? = null
        fun myClassResponseToMyClass(myClassResponse: MyClassResponse, mContext : Context) : MyClass{
            val myClass = MyClass()

            with(myClass){
                lastAccess = myClassResponse.lastAccess
                modules = myClassResponse.modules
                classPath = myClassResponse.classPath

                try {
                    val path = myClassResponse.classPath.split(" ")
                    LClassId = path[0]
                    MClassId = path[1]
                    SClassId = path[2]
                    subClassId = path[3]

                } catch (e : Exception){
                    Log.e(TAG, e.stackTraceToString())
                }

                // sqlite helper??? ????????? ???????????? ????????? ?????? ?????? ???????????? ????????????
                // ????????? dagger ????????? ???????????? ????????? ?????? ????????? ????????? ???????????? ???.
                if (sqliteSearchHelper == null) sqliteSearchHelper = SQLiteSearchHelper(mContext)
                MClassName = sqliteSearchHelper!!.getMClassName(LClassId, MClassId)
                subClassName = sqliteSearchHelper!!.getSubClassName(LClassId, MClassId, SClassId, subClassId)
            }

            return myClass
        }
    }
}