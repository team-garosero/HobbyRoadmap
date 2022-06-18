package com.garosero.android.hobbyroadmap.network.response

/**
 * Firebase 에서 데이터를 받아올때만 사용하는 클래스
 * 파싱할 때 사용한다.
 * 외부에서 직접 호출하지 않아야 한다.
 */
data class _CourseResponse(
    var xp : Int = 0 ,
    var title : String = "",
    var desc : String = "",
    var order : Int = 0,
    var courseID : String = ""
)
