package com.garosero.android.hobbyroadmap.network.request

abstract class RequestListener {

    open fun onRequestSuccess(data: Object){

    }

    open fun onRequestSuccess(){

    }

    /**
     * 빈 함수로, 데이터 호출 실패시
     * 로그를 찍거나 처리 로직을 적어야 할 때 override
     */
    fun onRequestFail() {

    }
}