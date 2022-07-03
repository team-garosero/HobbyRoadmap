package com.garosero.android.hobbyroadmap

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.garosero.android.hobbyroadmap.error.CanNotFindUidError
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.ReadRoadmapRequest
import com.garosero.android.hobbyroadmap.network.request.ReadTilRequest
import com.garosero.android.hobbyroadmap.network.request.ReadUserRequest
import com.garosero.android.hobbyroadmap.network.request.RequestListener
import com.garosero.android.hobbyroadmap.network.response._CategoryResponse
import com.garosero.android.hobbyroadmap.network.response._TilResponse
import com.garosero.android.hobbyroadmap.network.response._UserResponse
import com.google.firebase.auth.FirebaseAuth

class AppApplication : Application() {
    private val TAG = "AppApplication"

    companion object {
        var tilData = mutableMapOf<String, _TilResponse>()
        var categoryData = mutableMapOf<String, _CategoryResponse>()
        var userData = _UserResponse()

        private var uid : String? = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        /**
         * todo :
         * 앱이 시작할 때 데이터를 호출,
         * 추후에 로그인이 완료되면 데이터를 호출하도록 로직 변경이 필요.
         */
        with(NetworkFactory){
            request(ReadTilRequest(), object : RequestListener(){
                override fun onRequestSuccess(data: Object) {
                    tilData = data as MutableMap<String, _TilResponse>
                    //Log.e(TAG, tilData.toString())
                }
            })

            request(ReadUserRequest(), object : RequestListener(){
                override fun onRequestSuccess(data: Object) {
                    userData = data as _UserResponse
                    //Log.e(TAG, userData.toString())
                }
            })

            request(ReadRoadmapRequest(), object : RequestListener(){
                override fun onRequestSuccess(data: Object) {
                    categoryData = data as MutableMap<String, _CategoryResponse>
                    //Log.e(TAG, categoryData.toString())
                }
            })
        }
    }

    fun getUid() : String{
        if (uid!=null) return uid!!

        var uid = FirebaseAuth.getInstance().currentUser?.uid
        try {
            if(uid == null) {
                // 우선 throw error 던지도록 처리
                throw CanNotFindUidError()
            }
        } catch (e : CanNotFindUidError){
            /**
             * 지금 uid 값이 안들어옴 나중에 여기 처리해야 함
             */
            uid = "M8mYC1eUs6RqEUrxTj7mARW3dK72"
            Log.e(TAG, e.stackTraceToString())
        }

        return uid!!
    }

    // todo : 로그인 시 이 함수가 호출되어야 함.
    fun updateUid(uid : String){
        AppApplication.uid = uid
    }
}