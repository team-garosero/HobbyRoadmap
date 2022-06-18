package com.garosero.android.hobbyroadmap.network.response

/**
 * Firebase 에서 데이터를 받아올때만 사용하는 클래스
 * 파싱할 때 사용한다.
 * 외부에서 직접 호출하지 않아야 한다.
 */
data class _RoadmapResponse(
    var title : String = "", // ex. 백엔드
    var desc : String="", // 로드맵 전체 설명
    var timelimit : Int = 0,
    var percentage : Int = 0, // 0~100
    var subContentMap : MutableMap<String, _SubContentResponse> = mutableMapOf(),
    var roadmapID : String = ""
)