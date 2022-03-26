package com.garosero.android.hobbyroadmap.data

data class RoadmapItem(
    val title : String,
    var date : String,
    var percentage:Int = 0 // 0~100
)