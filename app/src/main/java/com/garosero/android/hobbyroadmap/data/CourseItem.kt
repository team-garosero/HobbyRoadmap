package com.garosero.android.hobbyroadmap.data

data class CourseItem(
    val courseID: String = "",
    val title: String,
    val desc:String,
    var xp:Int=0,
    val order:Int=0
)
