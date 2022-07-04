package com.garosero.android.hobbyroadmap.network.response

/**
 * Firebase 에서 데이터를 받아올때만 사용하는 클래스
 * 파싱할 때 사용한다.
 * 외부에서 직접 호출하지 않아야 한다.
 */
data class _UserResponse(
    var life : Int = 0,
    var name : String = "",
    var nickname : String = "",

    /**
     * TODO
     * 2022.07.03
     * myCategory는 roadmap의 version2.
     * category가 여러개 일때 roadmap 구조로는 category id를 알 수 없어서 우선 새로 만듦
     * 논의 후 하나만 사용하도록 수정 필요
     */
    var myCategory : Map<String, _MyCategoryResponse> = mapOf(),
    var roadmap : Map<String, _MyRoadmapResponse> = mapOf(),

    var token : String = "",
    var xp : Int = 0,
)
