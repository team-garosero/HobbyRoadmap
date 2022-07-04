package com.garosero.android.hobbyroadmap.data

data class RoadmapItem(
    var title : String = "", // ex. 백엔드
    var desc : String="", // 로드맵 전체 설명
    var timelimit : Int = 0,
    var percentage : Int = 0, // 0~100

    var last_access : String? = null,
    var imageLink : String? = null, //fixme : 아직 db에 반영되지 않은 컬럼
)