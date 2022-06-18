package com.garosero.android.hobbyroadmap.network.response

/**
 * Firebase 에서 데이터를 받아올때만 사용하는 클래스
 * 파싱할 때 사용한다.
 * 외부에서 직접 호출하지 않아야 한다.
 */
data class _CategoryResponse(
    var title:String = "",
    var roadmapMap: MutableMap<String, _RoadmapResponse> = mutableMapOf(),
    var categoryID : String = "",
)