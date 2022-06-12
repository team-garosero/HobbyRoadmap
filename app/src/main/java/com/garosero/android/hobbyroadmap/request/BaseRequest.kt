package com.garosero.android.hobbyroadmap.request

/**
 * 모든 request는 이 클래스를 상속 받도록 함
 */
abstract class BaseRequest {
    protected var mlistener : RequestListener? = null
    fun setListener(listener: RequestListener) {
        mlistener = listener
    }

    /**
     * 리퀘스트를 식별하는 태그
     */
    abstract val TAG : String

    /**
     * 데이터를 요청하는 코드
     * 하위 클래스에서 오버라이드 해서 써야 한다.
     */
    abstract fun request()
}