package com.garosero.android.hobbyroadmap

import android.app.Application
import android.util.Log
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.RequestListener
import com.garosero.android.hobbyroadmap.network.request.RoadmapRequest
import com.garosero.android.hobbyroadmap.network.request.TilRequest
import com.garosero.android.hobbyroadmap.network.request.UserRequest
import com.garosero.android.hobbyroadmap.network.response._CategoryResponse
import com.garosero.android.hobbyroadmap.network.response._UserResponse
import com.garosero.android.hobbyroadmap.network.response._RoadmapResponse
import com.garosero.android.hobbyroadmap.network.response._TilResponse

class AppApplication : Application() {
    private val TAG = "AppApplication"

    companion object {
        var tilData = mutableMapOf<String, _TilResponse>()
        var categoryData = mutableMapOf<String, _CategoryResponse>()
        var userData = _UserResponse()
    }

    override fun onCreate() {
        super.onCreate()

        /**
         * todo :
         * 앱이 시작할 때 데이터를 호출,
         * 추후에 로그인이 완료되면 데이터를 호출하도록 로직 변경이 필요.
         */
        with(NetworkFactory){
            request(TilRequest(), object : RequestListener(){
                override fun onRequestSuccess(data: Object) {
                    tilData = data as MutableMap<String, _TilResponse>
                    //Log.e(TAG, tilData.toString())
                }
            })

            request(UserRequest(), object : RequestListener(){
                override fun onRequestSuccess(data: Object) {
                    userData = data as _UserResponse
                    //Log.e(TAG, userData.toString())
                }
            })

            request(RoadmapRequest(), object : RequestListener(){
                override fun onRequestSuccess(data: Object) {
                    categoryData = data as MutableMap<String, _CategoryResponse>
                    //Log.e(TAG, categoryData.toString())
                }
            })
        }
    }
}