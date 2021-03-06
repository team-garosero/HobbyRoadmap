package com.garosero.android.hobbyroadmap

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.garosero.android.hobbyroadmap.network.NetworkFactory
import com.garosero.android.hobbyroadmap.network.request.ReadTilRequest
import com.garosero.android.hobbyroadmap.network.request.ReadUserRequest
import com.garosero.android.hobbyroadmap.network.request.RequestListener
import com.garosero.android.hobbyroadmap.network.response.TilResponse
import com.garosero.android.hobbyroadmap.network.response.UserResponse

class AppApplication : Application() {
    private val TAG = "AppApplication"

    companion object {
        var userData = MutableLiveData<UserResponse>()
        var tilData = MutableLiveData<Map<String, TilResponse>>()

        private var tilFlag = false
        private var userFlag = false

        fun requestSubscribeTil(){
            if (tilFlag){ // 이미 request가 등록되었다면,
                return;
            } // end if

            tilFlag = true

            NetworkFactory.request(ReadTilRequest(), object : RequestListener(){
                override fun onRequestSuccess(data: Any) {
                    tilData.value = data as Map<String, TilResponse>
                }
            })
        }

        fun requestSubscribeUser(){
            if (userFlag){
                return
            } // end if

            userFlag = true

            // get user data
            NetworkFactory.request(ReadUserRequest(), object : RequestListener() {
                override fun onRequestSuccess(data: Any) {
                    userData.value = data as UserResponse
                }
            })
        }
    }

}