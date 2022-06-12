package com.garosero.android.hobbyroadmap.request

abstract class RequestListener {
<<<<<<< Updated upstream
    abstract fun onRequestSuccess(query: Object, data : Object)
=======
    abstract fun onRequestSuccess(data : Object)
>>>>>>> Stashed changes

    /**
     * 빈 함수로, 데이터 호출 실패시
     * 로그를 찍거나 처리 로직을 적어야 할 때 override
     */
    fun onRequestFail() {

    }
}