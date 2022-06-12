package com.garosero.android.hobbyroadmap

import android.app.Application
import android.util.Log
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.request.BaseRequest
import com.garosero.android.hobbyroadmap.request.RequestListener
import com.garosero.android.hobbyroadmap.request.TilItemRequest
import java.util.*
import kotlin.collections.ArrayList

class AppApplication : Application() {
    private val TAG = "AppApplication"

    companion object {
        open var tilData = ArrayList<TilItem>()

        /**
         * 실행되고 있는 리쿼스트 tag 를 저장
         * 스레드 객체에서 호출될 것을 고려해 vector을 활용
         * 중복 호출을 막기 위한 장치
         */
        private var requestPool = Vector<String>()

        /**
         * 리퀘스트 객체를 static 객체로 저장해둔다.
         */
    }

    override fun onCreate() {
        super.onCreate()

        initTilRequest()
    }

    /**
     * 이미 requestPool에 등록되었다면, 함수를 종료한다.
     */
    private fun initTilRequest(){
        val tilItemRequest = TilItemRequest()
        if (requestPool.contains(tilItemRequest.TAG)) return

        tilItemRequest.setListener(object : RequestListener() {
            override fun onRequestSuccess(data: Object) {
                tilData = data as ArrayList<TilItem>
                Log.e(TAG, tilData.toString())
            }
        })

        registerRequest(tilItemRequest)
    }

    private fun registerRequest(request: BaseRequest){
        request.request()
        requestPool.add(request.TAG)
    }

}