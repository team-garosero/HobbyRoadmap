package com.garosero.android.hobbyroadmap.data

data class RoadmapItem(
    val title : String, // ex. 백엔드
    val roadmapID : String = "",
    val desc : String="", // 로드맵 전체 설명
    var timelimit : String,
    var percentage : Int = 0, // 0~100
    val courseList:MutableList<CourseItem> = mutableListOf()
)