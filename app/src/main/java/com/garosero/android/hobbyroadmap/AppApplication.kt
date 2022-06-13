package com.garosero.android.hobbyroadmap

import android.app.Application
import android.util.Log
import com.garosero.android.hobbyroadmap.data.RoadmapItem
import com.garosero.android.hobbyroadmap.data.TilItem
import com.garosero.android.hobbyroadmap.request.BaseRequest
import com.garosero.android.hobbyroadmap.request.RequestListener
import com.garosero.android.hobbyroadmap.request.RoadmapRequest
import com.garosero.android.hobbyroadmap.request.TilItemRequest
import java.util.*
import kotlin.collections.ArrayList

class AppApplication : Application() {
    private val TAG = "AppApplication"

    companion object {
        open var tilArraylist = ArrayList<TilItem>()
        open var roadmapMutableMap = mutableMapOf<String, RoadmapItem>()

        /**
         * 실행되고 있는 리쿼스트 tag 를 저장
         * 스레드 객체에서 호출될 것을 고려해 vector을 활용
         * 중복 호출을 막기 위한 장치
         */
        private var requestPool = Vector<String>()
    }

    override fun onCreate() {
        super.onCreate()

        initTilRequest()
        initRoadmapRequest()
    }

    //----------------------------------------------------------------------
    // init request
    //----------------------------------------------------------------------

    private fun initTilRequest(){
        val tilItemRequest = TilItemRequest()
        // 이미 requestPool에 등록되었다면, 함수를 종료한다.
        if (requestPool.contains(tilItemRequest.TAG)) return

        tilItemRequest.setListener(object : RequestListener() {
            override fun onRequestSuccess(data: Object) {
                tilArraylist = data as ArrayList<TilItem>
                //Log.e(TAG, tilArraylist.toString())
            }
        })

        registerRequest(tilItemRequest)
    }

    private fun initRoadmapRequest(){
        val roadmapRequest = RoadmapRequest()
        // 이미 requestPool에 등록되었다면, 함수를 종료한다.
        if (requestPool.contains(roadmapRequest.TAG)) return
        
        roadmapRequest.setListener(object : RequestListener(){
            override fun onRequestSuccess(data: Object) {
                roadmapMutableMap = data as MutableMap<String, RoadmapItem>
                // 한번만 불러오는 코드이므로 데이터를 불러오는 것을 완료했다면, 더이상 동작하지 않는다.
                // requestPool에는 동작 중인 request 만 저장하므로 제거해주어야 한다.
                unRegisterRequest(roadmapRequest)

                //Log.e(TAG, roadmapMutableMap.toString())
            }
        })

        // 한번만 호출하는 코드이므로, 추후에 외부에서 호출 가능하도록 처리해야 할 수 있다.
        registerRequest(roadmapRequest)
    }

    //----------------------------------------------------------------------
    // request pool 을 관리
    // 외부에서 호출할수 없음
    //----------------------------------------------------------------------

    private fun registerRequest(request: BaseRequest){
        request.request()
        requestPool.add(request.TAG)
    }

    private fun unRegisterRequest(request: BaseRequest){
        requestPool.removeElement(request)
        // todo : request를 중지하는 코드가 포함되어야 함
    }
}