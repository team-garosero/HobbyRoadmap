package com.garosero.android.hobbyroadmap.network.response


/**
 * Firebase 에서 데이터를 받아올때만 사용하는 클래스
 * 파싱할 때 사용한다.
 * 외부에서 직접 호출하지 않아야 한다.
 */
data class _TilResponse(
    var uid : String = "",
    var courseID : String = "",
    var content: String = "",
    var date: String = ""
){
    var tilId : String = ""
    private fun getTilID(){}
}